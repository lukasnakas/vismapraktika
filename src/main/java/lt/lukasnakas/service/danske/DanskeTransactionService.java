package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.exception.TransactionRetrievalException;
import lt.lukasnakas.mapper.ITransactionMapper;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
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
public class DanskeTransactionService implements ITransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DanskeTransactionService.class);

	private final DanskeServiceConfiguration danskeServiceConfiguration;
	private final DanskeTokenRenewalService danskeTokenRenewalService;
	private final ITransactionMapper transactionMapper;
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;

	public DanskeTransactionService(DanskeServiceConfiguration danskeServiceConfiguration,
									DanskeTokenRenewalService danskeTokenRenewalService,
									ITransactionMapper transactionMapper,
									RestTemplate restTemplate,
									HttpHeaders httpHeaders) {
		this.danskeServiceConfiguration = danskeServiceConfiguration;
		this.danskeTokenRenewalService = danskeTokenRenewalService;
		this.transactionMapper = transactionMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	public List<CommonTransaction> retrieveTransactions() {
		ResponseEntity<List<DanskeTransaction>> responseEntity;

		try {
			String accessToken = danskeServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForTransactions(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = danskeTokenRenewalService.generateAccessToken().getToken();
			responseEntity = getResponseEntityForTransactions(accessToken);
		} catch (Exception e) {
			throw new TransactionRetrievalException(e.getMessage());
		}

		LOGGER.info("[{}] GET transactions [Status Code: {}]",
				danskeServiceConfiguration.getName(), responseEntity.getStatusCode());

		return Optional.ofNullable(responseEntity.getBody())
				.orElseThrow(() -> new TransactionRetrievalException("Failed to retrieve transactions"))
				.stream()
				.map(transactionMapper::danskeTransactionToCommonTransaction)
				.collect(Collectors.toList());
	}

	private ResponseEntity<List<DanskeTransaction>> getResponseEntityForTransactions(String accessToken) {
		return restTemplate.exchange(
				danskeServiceConfiguration.getUrlAccountTransactions(),
				HttpMethod.GET,
				getHttpEntity(accessToken),
				new ParameterizedTypeReference<List<DanskeTransaction>>() {
				});
	}

	private HttpEntity<String> getHttpEntity(String accessToken) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(accessToken);
		return new HttpEntity<>(httpHeaders);
	}

}