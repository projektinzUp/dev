package ProjektInz.RESTAPI.Controllers;

import ProjektInz.RESTAPI.Service.OlxAdvertService;
import ProjektInz.RESTAPI.restApi.OlxAdvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class advertController {

    @Autowired
    OlxAdvertService olxAdvertService;

    @GetMapping("/")
    public String index(@RequestParam(name="olxCode", required = false) String olxCode) {
        //dosomething with olxCode
        System.out.println("olxCode: " + olxCode);
        if (olxCode == "" || olxCode == null) {
            return "index";
        } else {
            return "home";
        }
    }
//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping(path = {"/olxAdvertsList"})
    public String home(OlxAdvert olxAdvert, Model model, String keyword) {
        if (keyword != null) {
            List<OlxAdvert> list = olxAdvertService.getByKeyword(keyword);
            model.addAttribute("list", list);
        } else {
            List<OlxAdvert> list = olxAdvertService.findAllAdverts();
            model.addAttribute("list", list);
        }
        return "olx-adverts-list";
    }

    @GetMapping("/olxGetCode")
    public String send(@RequestParam(name="olxCode", required = false) String olxCode) {
        //dosomething with olxCode
        System.out.println("olxCode: " + olxCode);
        return "olx-get-code";
    }

}
