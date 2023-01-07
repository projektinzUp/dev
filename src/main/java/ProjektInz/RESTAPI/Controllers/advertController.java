package ProjektInz.RESTAPI.Controllers;

import ProjektInz.RESTAPI.Service.Adverts.AdvertService;
import ProjektInz.RESTAPI.Service.Olx.OlxAdvertService;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvert;
import ProjektInz.RESTAPI.restApi.Olx.OlxAdvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class advertController {

    @Autowired
    AdvertService advertService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping(path = {"/allAdverts"})
    public String home(AllegroAdvert allegroAdvert, Model model, String keyword) {
        if (keyword != null) {
            List<AllegroAdvert> list = advertService.getByKeyword(keyword);
            model.addAttribute("list", list);
        } else {
            List<AllegroAdvert> list = advertService.findAllAdverts();
            model.addAttribute("list", list);
        }
        return "all-adverts";
    }

    @RequestMapping(path = {"/allSortAdvertsDesc"})
    public String allSortAdvertsDesc(AllegroAdvert allegroAdvert, Model model) {
        List<AllegroAdvert> list = advertService.sortByTitleDesc();
        model.addAttribute("list", list);
        return "all-adverts";
    }

    @RequestMapping(path = {"/allSortAdvertsAsc"})
    public String allSortAdvertsAsc(AllegroAdvert allegroAdvert, Model model) {
        List<AllegroAdvert> list = advertService.sortByTitleAsc();
        model.addAttribute("list", list);
        return "all-adverts";
    }

    @RequestMapping(path = {"/allSortAdvertsByPriceDesc"})
    public String allSortAdvertsByPriceDesc(AllegroAdvert allegroAdvert, Model model) {
        List<AllegroAdvert> list = advertService.sortByPriceDesc();
        model.addAttribute("list", list);
        return "all-adverts";
    }

    @RequestMapping(path = {"/allSortAdvertsByPriceAsc"})
    public String allSortAdvertsByPriceAsc(AllegroAdvert allegroAdvert, Model model) {
        List<AllegroAdvert> list = advertService.sortByPriceAsc();
        model.addAttribute("list", list);
        return "all-adverts";
    }

}
