package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionExecutionExeption;
import lt.lukasnakas.mapper.IPaymentMapper;
import lt.lukasnakas.mapper.ITransactionMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RevolutPaymentService implements IPaymentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RevolutPaymentService.class);

	private final RevolutServiceConfiguration revolutServiceConfiguration;
	private final RevolutTokenRenewalService revolutTokenRenewalService;
	private final RevolutPaymentValidationService revolutPaymentValidationService;
	private final ITransactionMapper transactionMapper;
	private final IPaymentMapper paymentMapper;
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;

	public RevolutPaymentService(RevolutServiceConfiguration revolutServiceConfiguration,
								 RevolutTokenRenewalService revolutTokenRenewalService,
								 RevolutPaymentValidationService revolutPaymentValidationService,
								 ITransactionMapper transactionMapper,
								 IPaymentMapper paymentMapper,
								 RestTemplate restTemplate,
								 HttpHeaders httpHeaders) {
		this.revolutServiceConfiguration = revolutServiceConfiguration;
		this.revolutTokenRenewalService = revolutTokenRenewalService;
		this.revolutPaymentValidationService = revolutPaymentValidationService;
		this.transactionMapper = transactionMapper;
		this.paymentMapper = paymentMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	public CommonTransaction postPayment(Payment payment) {
		ResponseEntity<RevolutTransaction> responseEntity;

		try {
			String accessToken = revolutServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForPayment(accessToken, payment);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
			responseEntity = getResponseEntityForPayment(accessToken, payment);
		} catch (Exception e) {
			throw new TransactionExecutionExeption(e.getMessage());
		}

		LOGGER.info("[{}] POST payment [Status Code: {}]",
				revolutServiceConfiguration.getName(), responseEntity.getStatusCode());

		return transactionMapper.revolutTransactionToCommonTransaction(
				Optional.ofNullable(responseEntity.getBody())
						.orElseThrow(() -> new TransactionExecutionExeption("Failed to execute transaction")),
				payment);
	}

	private ResponseEntity<RevolutTransaction> getResponseEntityForPayment(String accessToken, Payment payment) {
		return restTemplate.exchange(
				revolutServiceConfiguration.getUrlAccountPayment(),
				HttpMethod.POST,
				getHttpEntity(accessToken, payment),
				RevolutTransaction.class);
	}

	private HttpEntity<?> getHttpEntity(String accessToken, Payment payment) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(accessToken);
		return new HttpEntity<>(payment, httpHeaders);
	}

	public String getBankName() {
		return revolutServiceConfiguration.getName();
	}

	public CommonTransaction executePaymentIfValid(PaymentDTO paymentDTO) {
		RevolutPayment revolutPayment = paymentMapper.paymentDtoToRevolutPayment(paymentDTO);

		if (revolutPaymentValidationService.isValid(revolutPayment)) {
			return postPayment(revolutPayment);
		} else {
			TransactionError transactionError = revolutPaymentValidationService.getErrorWithMissingParamsFromPayment(revolutPayment);
			throw new BadRequestException(transactionError.getMessage());
		}
	}
}