package lt.lukasnakas.service.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.exception.AccountRetrievalException;
import lt.lukasnakas.mapper.IAccountMapper;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.service.IAccountService;
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
public class RevolutAccountService implements IAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RevolutAccountService.class);

	private final RevolutServiceConfiguration revolutServiceConfiguration;
	private final RevolutTokenRenewalService revolutTokenRenewalService;
	private final IAccountMapper accountMapper;
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;

	public RevolutAccountService(RevolutServiceConfiguration revolutServiceConfiguration,
								 RevolutTokenRenewalService revolutTokenRenewalService,
								 IAccountMapper accountMapper,
								 RestTemplate restTemplate,
								 HttpHeaders httpHeaders) {
		this.revolutServiceConfiguration = revolutServiceConfiguration;
		this.revolutTokenRenewalService = revolutTokenRenewalService;
		this.accountMapper = accountMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	public List<CommonAccount> retrieveAccounts() {
		ResponseEntity<List<RevolutAccount>> responseEntity;

		try {
			String accessToken = revolutServiceConfiguration.getAccessToken();
			responseEntity = getResponseEntityForAccounts(accessToken);
		} catch (HttpClientErrorException.Unauthorized e) {
			String accessToken = revolutTokenRenewalService.generateAccessToken().getToken();
			responseEntity = getResponseEntityForAccounts(accessToken);
		} catch (Exception e) {
			throw new AccountRetrievalException(e.getMessage());
		}

		LOGGER.info("[{}] GET accounts [Status Code: {}]",
				revolutServiceConfiguration.getName(), responseEntity.getStatusCode());

		return Optional.ofNullable(responseEntity.getBody())
				.orElseThrow(() -> new AccountRetrievalException("Failed to retrieve accounts"))
				.stream()
				.map(accountMapper::revolutAccountToCommonAccount)
				.collect(Collectors.toList());
	}

	private ResponseEntity<List<RevolutAccount>> getResponseEntityForAccounts(String accessToken) {
		return restTemplate.exchange(
				revolutServiceConfiguration.getUrlAccounts(),
				HttpMethod.GET,
				getHttpEntity(accessToken),
				new ParameterizedTypeReference<List<RevolutAccount>>() {
				});
	}

	private HttpEntity<String> getHttpEntity(String accessToken) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(accessToken);
		return new HttpEntity<>(httpHeaders);
	}

}