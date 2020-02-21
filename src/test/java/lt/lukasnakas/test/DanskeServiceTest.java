package lt.lukasnakas.test;

import lt.lukasnakas.configuration.DanskeServiceConfiguration;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.service.danske.DanskeService;
import lt.lukasnakas.util.MockedDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DanskeServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private DanskeServiceConfiguration danskeServiceConfiguration;

	@Mock
	private HttpHeaders httpHeaders;

	@InjectMocks
	private DanskeService danskeService;

	private MockedDataGenerator mockedDataGenerator = new MockedDataGenerator();

	@Test
	public void retrieveAccounts_shouldReturnTrue_whenComparingFirstElements() {
		ResponseEntity<DanskeAccount> responseEntity = mockedDataGenerator.getMockedDanskeAccountResponseEntity();
		CommonAccount commonAccount = mockedDataGenerator.generateCommonAccount(responseEntity.getBody());

		when(danskeService.getResponseEntityForAccounts()).thenReturn(responseEntity);

		List<CommonAccount> expected = Collections.singletonList(commonAccount);
		List<CommonAccount> actual = danskeService.retrieveAccounts();

		assertEquals(expected.get(0), actual.get(0));
	}

}
