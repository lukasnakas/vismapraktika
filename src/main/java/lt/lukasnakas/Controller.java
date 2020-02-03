package lt.lukasnakas;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/")
    public String Greet(){
        return "Hello!";
    }

    @RequestMapping("/banks/{id}")
    public String getBank(@PathVariable String id){
        return "Bankas" + id;
    }

}
