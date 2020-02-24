package lt.lukasnakas.test;

import javafx.beans.binding.MapBinding;
import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.AccessToken;
import lt.lukasnakas.service.danske.DanskeTokenRenewalService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DanskeTokenRenewalServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private DanskeServiceConfiguration danskeServiceConfiguration;

	@InjectMocks
	private DanskeTokenRenewalService danskeTokenRenewalService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void generateAccessToken_shouldReturnTrue_whenComparingAccessTokens() {
		ResponseEntity<AccessToken> responseEntity = testDataGenerator.getExpectedDanskeAccessTokenResponseEntity();

		when(danskeTokenRenewalService.getAccessTokenResponseEntity()).thenReturn(responseEntity);

		AccessToken expected = responseEntity.getBody();
		AccessToken actual = danskeTokenRenewalService.generateAccessToken();

		assertEquals(expected, actual);
	}

	@Test
	public void extractAccessToken_shouldReturnTrue_whenComparingAccessTokens() {
		ResponseEntity<AccessToken> responseEntity = testDataGenerator.getExpectedDanskeAccessTokenResponseEntity();

		AccessToken expected = responseEntity.getBody();
		AccessToken actual = danskeTokenRenewalService.extractAccessToken(responseEntity);

		assertEquals(expected, actual);
	}
}
