package lt.lukasnakas;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


@RestController
public class Controller {

    @RequestMapping("/")
    public String Greet(){
        return "Hello!";
    }

    @RequestMapping("/accounts")
    public String getAccounts(){

        String URL = "https://sandbox-b2b.revolut.com/api/1.0/accounts";
        String tokenType = "Bearer";
        String accessToken = "oa_sand_jh8VlUYb9WGKqKRAUqREt5og4qsNLGyemc2jv2VoSnk";
        String authorizationHeader = tokenType + " " + accessToken;
        String type = "";

        try {
            HttpResponse<String> httpResponse = Unirest.get(URL).header("Content-Type", "application/json").header("Authorization", authorizationHeader).asString();
            System.out.println(httpResponse.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return type;
    }

}
