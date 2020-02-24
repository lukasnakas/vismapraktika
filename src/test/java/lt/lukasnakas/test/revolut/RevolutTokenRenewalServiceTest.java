package lt.lukasnakas.test.revolut;

import lt.lukasnakas.configuration.RevolutServiceConfiguration;
import lt.lukasnakas.model.AccessToken;
import lt.lukasnakas.model.revolut.RevolutAccessToken;
import lt.lukasnakas.service.revolut.RevolutTokenRenewalService;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RevolutTokenRenewalServiceTest {

	@Mock
	private RevolutServiceConfiguration revolutServiceConfiguration;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private RevolutTokenRenewalService revolutTokenRenewalService;

	private TestDataGenerator testDataGenerator = new TestDataGenerator();

	@Test
	public void generateAccessToken_shouldReturnTrue_whenComparingAccessTokens() {
		ResponseEntity<RevolutAccessToken> responseEntity = testDataGenerator.getExpectedRevolutAccessTokenResponseEntity();

		when(revolutTokenRenewalService.getRevolutAccessTokenResponseEntity()).thenReturn(responseEntity);

		AccessToken expected = responseEntity.getBody();
		AccessToken actual = revolutTokenRenewalService.generateAccessToken();

		assertEquals(expected, actual);
	}

	@Test
	public void extractAccessToken_shouldReturnTrue_whenComparingAccessTokens() {
		ResponseEntity<RevolutAccessToken> responseEntity = testDataGenerator.getExpectedRevolutAccessTokenResponseEntity();

		AccessToken expected = responseEntity.getBody();
		AccessToken actual = revolutTokenRenewalService.extractAccessToken(responseEntity);

		assertEquals(expected, actual);
	}

}
