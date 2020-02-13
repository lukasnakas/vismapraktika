package lt.lukasnakas;

import lt.lukasnakas.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void accountsPathShouldContainAccount() throws Exception {
		String exampleAccount = "{\"id\":\"bc75304e-649c-45b3-a606-5277b6810d64\",\"name\":\"Payments\",\"balance\":5300.0,\"currency\":\"AUD\",\"state\":\"active\",\"publicAccount\":false,\"created_at\":\"2020-02-04T08:56:33.532130Z\",\"updated_at\":\"2020-02-04T08:56:33.532130Z\"}";
		String url = "http://localhost:" + port + "/api/accounts";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		assertThat(testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class).getBody()).contains(exampleAccount);
	}

	@Test
	public void transactionsPathShouldContainTransaction() throws Exception {
		String exampleTransaction = "{\"id\":\"c37c736a-d762-4db9-9a24-6a12821bf1cc\",\"CreditDebitIndicator\":\"Credit\",\"Amount\":{\"Amount\":1144.0,\"Currency\":\"EUR\"}}";
		String url = "http://localhost:" + port + "/api/transactions";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		assertThat(testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class).getBody()).contains(exampleTransaction);
	}

}
