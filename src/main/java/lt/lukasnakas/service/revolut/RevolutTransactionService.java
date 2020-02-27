package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.exception.TransactionRetrievalException;
import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import lt.lukasnakas.service.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RevolutTransactionService implements ITransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RevolutTransactionService.class);

	private final RevolutServiceConfiguration revolutServiceConfiguration;
	private final RevolutTokenRenewalService revolutTokenRenewalService;
	private final TransactionMapper transactionMapper;
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;

	public RevolutTransactionService(RevolutServiceConfiguration revolutServiceConfiguration,
									 RevolutTokenRenewalService revolutTokenRenewalService,
									 TransactionMapper transactionMapper,
									 RestTemplate restTemplate,
									 HttpHeaders httpHeaders) {
		this.revolutServiceConfiguration = revolutServiceConfiguration;
		this.revolutTokenRenewalService = revolutTokenRenewalService;
		this.transactionMapper = transactionMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	public List<CommonTransaction> retrieveTransactions() {
		ResponseEntity<List<RevolutTransaction>> responseEntity;

		try {
			String accessToken = revolutServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForTransactions(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
			responseEntity = getResponseEntityForTransactions(accessToken);
		} catch (Exception e) {
			throw new TransactionRetrievalException(e.getMessage());
		}

		LOGGER.info("[{}] GET transactions [Status Code: {}]",
				revolutServiceConfiguration.getName(), responseEntity.getStatusCode());

		return Optional.ofNullable(responseEntity.getBody())
				.orElseThrow(() -> new TransactionRetrievalException("Failed to retrieve transactions"))
				.stream()
				.filter(revolutTransaction -> (revolutTransaction.getLegs()[0].getCounterparty() != null))
				.map(transactionMapper::revolutTransactionToCommonTransaction)
				.collect(Collectors.toList());
	}

	private ResponseEntity<List<RevolutTransaction>> getResponseEntityForTransactions(String accessToken) {
		return restTemplate.exchange(
				revolutServiceConfiguration.getUrlAccountTransactions(),
				HttpMethod.GET,
				getHttpEntity(accessToken),
				new ParameterizedTypeReference<List<RevolutTransaction>>() {
				});
	}

	private HttpEntity<String> getHttpEntity(String accessToken) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(accessToken);
		return new HttpEntity<>(httpHeaders);
	}

}