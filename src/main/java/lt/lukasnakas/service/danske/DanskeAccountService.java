package lt.lukasnakas.service.danske;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.exception.AccountRetrievalException;
import lt.lukasnakas.mapper.IAccountMapper;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DanskeAccountService implements IAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DanskeAccountService.class);

	private final DanskeServiceConfiguration danskeServiceConfiguration;
	private final IAccountMapper accountMapper;
	private final RestTemplate restTemplate;
	private final HttpHeaders httpHeaders;

	public DanskeAccountService(DanskeServiceConfiguration danskeServiceConfiguration,
								IAccountMapper accountMapper,
								RestTemplate restTemplate,
								HttpHeaders httpHeaders) {
		this.danskeServiceConfiguration = danskeServiceConfiguration;
		this.accountMapper = accountMapper;
		this.restTemplate = restTemplate;
		this.httpHeaders = httpHeaders;
	}

	public List<CommonAccount> retrieveAccounts() {
		ResponseEntity<DanskeAccount> responseEntity;

		try {
			responseEntity = getResponseEntityForAccounts();
		} catch (HttpClientErrorException.Unauthorized e) {
			responseEntity = getResponseEntityForAccounts();
		} catch (Exception e) {
			throw new AccountRetrievalException(e.getMessage());
		}

		LOGGER.info("[{}] GET accounts [Status Code: {}]",
				danskeServiceConfiguration.getName(), responseEntity.getStatusCode());

		return Collections.singletonList(
				accountMapper.danskeAccountToCommonAccount(Optional.ofNullable(responseEntity.getBody())
						.orElseThrow(() -> new AccountRetrievalException("Failed to retrieve accounts"))));
	}

	private ResponseEntity<DanskeAccount> getResponseEntityForAccounts() {
		return restTemplate.exchange(
				danskeServiceConfiguration.getUrlAccountsVirtual(),
				HttpMethod.GET,
				getHttpEntityVirtual(),
				new ParameterizedTypeReference<DanskeAccount>() {
				});
	}

	private HttpEntity<String> getHttpEntityVirtual() {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.clear();
		httpHeaders.add("Authorization", danskeServiceConfiguration.getAccessTokenVirtual());
		httpHeaders.add("x-fapi-financial-id", "0015800000jf7AeAAI");
		return new HttpEntity<>(httpHeaders);
	}

}