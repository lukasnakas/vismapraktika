import lt.lukasnakas.service.revolut.RevolutAccountService;
import org.junit.Assert;
import org.junit.Test;

public class RevolutControllerTest {

   @Test
    public void TestGetParsedAccounts() throws Exception{
       String assertion = "[\n" +
               "    {\n" +
               "        \"id\": \"123456789\",\n" +
               "        \"name\": \"Main\",\n" +
               "        \"balance\": 28600.0,\n" +
               "        \"currency\": \"GBP\",\n" +
               "        \"state\": \"active\",\n" +
               "        \"publicAccount\": false,\n" +
               "        \"created_at\": \"2020-02-04T08:56:33.473+0000\",\n" +
               "        \"updated_at\": \"2020-02-04T08:56:33.533+0000\"\n" +
               "    }]";
       String expected = "123456789";

       RevolutAccountService revolutAccountService = new RevolutAccountService();
       Assert.assertEquals(expected, revolutAccountService.getParsedAccounts(assertion).get(0).getId());
   }

}
