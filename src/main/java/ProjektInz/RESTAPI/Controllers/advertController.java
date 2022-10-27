package ProjektInz.RESTAPI.Controllers;

import ProjektInz.RESTAPI.Service.OlxAdvertService;
import ProjektInz.RESTAPI.restApi.OlxAdvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class advertController {

    @Autowired
    OlxAdvertService olxAdvertService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/allAdverts")
    public String showAdverts(Model model){
        model.addAttribute("allAdverts", olxAdvertService.findAllAdverts());
        return "all-adverts";
    }

    @RequestMapping(path = {"/", "/search"})
    public String home(OlxAdvert olxAdvert, Model model, String keyword) {
        if (keyword != null) {
            List<OlxAdvert> list = olxAdvertService.getByKeyword(keyword);
            model.addAttribute("list", list);
        } else {
            List<OlxAdvert> list = olxAdvertService.findAllAdverts();
            model.addAttribute("list", list);
        }
        return "search";
    }
}
