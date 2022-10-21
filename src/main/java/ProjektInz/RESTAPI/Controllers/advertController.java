package ProjektInz.RESTAPI.Controllers;

import ProjektInz.RESTAPI.Service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class advertController {

    @Autowired
    AdvertService advertService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/allAdverts")
    public String showAdverts(Model model){
        model.addAttribute("allAdverts", advertService.findAllAdverts());
        return "all-adverts";
    }
}
