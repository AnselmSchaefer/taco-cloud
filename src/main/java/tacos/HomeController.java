package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home2() {
		//this name directly maps to the html page in the templates folder!
		return "home";
	}

}
