import lt.lukasnakas.Service.Danske.DanskeAccountService;
import org.junit.Assert;
import org.junit.Test;

public class DanskeControllerTest {

   @Test
    public void TestGetParsedAccounts() throws Exception{
       String assertion = "[\n" +
               "    {\n" +
               "        \"id\": \"11111\",\n" +
               "        \"account\": [\n" +
               "            {\n" +
               "                \"name\": \"Credit Card Account\",\n" +
               "                \"schemeName\": \"IBAN\",\n" +
               "                \"identification\": \"MT74JOAZ7830798911T272D36939OL7\",\n" +
               "                \"secondaryIdentification\": \"YVLICEF1AAJ\"\n" +
               "            }\n" +
               "        ]\n" +
               "    }\n" +
               "]";
       String expected = "11111";

       DanskeAccountService danskeAccountService = new DanskeAccountService(null);
       Assert.assertEquals(expected, danskeAccountService.getParsedAccounts(assertion).get(0).getId());
   }

}
