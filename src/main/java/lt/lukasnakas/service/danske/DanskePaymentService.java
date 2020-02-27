package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.exception.BadRequestException;
import lt.lukasnakas.exception.TransactionExecutionExeption;
import lt.lukasnakas.mapper.IPaymentMapper;
import lt.lukasnakas.mapper.ITransactionMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.TransactionError;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.dto.PaymentDTO;
import lt.lukasnakas.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class DanskePaymentService implements IPaymentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DanskePaymentService.class);

	private final DanskeServiceConfiguration danskeServiceConfiguration;
	private final DanskeTokenRenewalService danskeTokenRenewalService;
	private final DanskePaymentValidationService danskePaymentValidationService;
	private final ITransactionMapper transactionMapper;
	private final IPaymentMapper paymentMapper;
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;

	public DanskePaymentService(DanskeServiceConfiguration danskeServiceConfiguration,
								DanskeTokenRenewalService danskeTokenRenewalService,
								DanskePaymentValidationService danskePaymentValidationService,
								ITransactionMapper transactionMapper,
								IPaymentMapper paymentMapper,
								RestTemplate restTemplate,
								HttpHeaders httpHeaders) {
		this.danskeServiceConfiguration = danskeServiceConfiguration;
		this.danskeTokenRenewalService = danskeTokenRenewalService;
		this.danskePaymentValidationService = danskePaymentValidationService;
		this.transactionMapper = transactionMapper;
		this.paymentMapper = paymentMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	public CommonTransaction postPayment(Payment payment) {
		ResponseEntity<DanskeTransaction> responseEntity;

		try {
			String accessToken = danskeServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForPayment(accessToken, payment);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = danskeTokenRenewalService.generateAccessToken().getToken();
			responseEntity = getResponseEntityForPayment(accessToken, payment);
		} catch (Exception e) {
			throw new TransactionExecutionExeption(e.getMessage());
		}

		LOGGER.info("[{}] POST payment [Status Code: {}]",
				danskeServiceConfiguration.getName(), responseEntity.getStatusCode());

		return transactionMapper.danskeTransactionToCommonTransaction(Optional.ofNullable(responseEntity.getBody())
				.orElseThrow(() -> new TransactionExecutionExeption("Failed to execute transaction")));
	}

	private ResponseEntity<DanskeTransaction> getResponseEntityForPayment(String accessToken, Payment payment) {
		return restTemplate.exchange(
				danskeServiceConfiguration.getUrlAccountTransactions(),
				HttpMethod.POST,
				getHttpEntity(accessToken, payment),
				DanskeTransaction.class);
	}

	private HttpEntity<?> getHttpEntity(String accessToken, Payment payment) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(accessToken);
		return new HttpEntity<>(payment, httpHeaders);
	}

	public String getBankName() {
		return danskeServiceConfiguration.getName();
	}

	public boolean isPaymentBodyValid(PaymentDTO paymentDTO) {
		return danskePaymentValidationService.isValid(paymentDTO);
	}

	public TransactionError getTransactionErrorWithMissingParams(PaymentDTO paymentDTO) {
		return danskePaymentValidationService.getErrorWithMissingParamsFromPayment(paymentDTO);
	}

	public CommonTransaction executePaymentIfValid(PaymentDTO paymentDTO) {
		Payment payment = paymentMapper.paymentDtoToPayment(paymentDTO);

		if (danskePaymentValidationService.isValid(paymentDTO)) {
			return postPayment(payment);
		} else {
			TransactionError transactionError = danskePaymentValidationService.getErrorWithMissingParamsFromPayment(paymentDTO);
			throw new BadRequestException(transactionError.getMessage());
		}
	}

}