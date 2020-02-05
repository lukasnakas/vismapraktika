package lt.lukasnakas.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @RequestMapping("/")
    public String Greet(){
        return "Hello!";
    }

}
