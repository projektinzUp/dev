package ProjektInz.RESTAPI.Controllers;

import ProjektInz.RESTAPI.Service.AdvertService;
import ProjektInz.RESTAPI.restApi.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(path = {"/","/search"})
    public String home(Advert advert, Model model, String keyword) {
        if(keyword!=null) {
            List<Advert> list = advertService.getByKeyword(keyword);
            model.addAttribute("list", list);
        }else {
            List<Advert> list = advertService.findAllAdverts();
            model.addAttribute("list", list);
        }
        return "search";
    }
}
