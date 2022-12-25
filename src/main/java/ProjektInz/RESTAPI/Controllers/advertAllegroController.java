package ProjektInz.RESTAPI.Controllers;

import ProjektInz.RESTAPI.Service.AllegroAdvertService;
import ProjektInz.RESTAPI.Service.OlxAdvertService;
import ProjektInz.RESTAPI.restApi.AllegroAdvert;
import ProjektInz.RESTAPI.restApi.OlxAdvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class advertAllegroController {
    @Autowired
    AllegroAdvertService allegroAdvertService;

    @RequestMapping(path = {"/allegroAdverts"})
    public String home(OlxAdvert olxAdvert, Model model, String keyword) {
        if (keyword != null) {
            List<AllegroAdvert> list = allegroAdvertService.getByKeyword(keyword);
            model.addAttribute("list", list);
        } else {
            List<AllegroAdvert> list = allegroAdvertService.findAllAdverts();
            model.addAttribute("list", list);
        }
        return "allegro-adverts";
    }

    @RequestMapping(path = {"/allegroSortAdvertsDesc"})
    public String allegroSortAdvertsDesc(OlxAdvert olxAdvert, Model model) {
        List<AllegroAdvert> list = allegroAdvertService.sortByTitleDesc();
        model.addAttribute("list", list);
        return "allegro-adverts";
    }

    @RequestMapping(path = {"/allegroSortAdvertsAsc"})
    public String allegroSortAdvertsAsc(OlxAdvert olxAdvert, Model model) {
        List<AllegroAdvert> list = allegroAdvertService.sortByTitleAsc();
        model.addAttribute("list", list);
        return "allegro-adverts";
    }

    @RequestMapping(path = {"/allegroSortAdvertsByPriceDesc"})
    public String allegroSortAdvertsByPriceDesc(OlxAdvert olxAdvert, Model model) {
        List<AllegroAdvert> list = allegroAdvertService.sortByPriceDesc();
        model.addAttribute("list", list);
        return "allegro-adverts";
    }

    @RequestMapping(path = {"/allegroSortAdvertsByPriceAsc"})
    public String allegroSortAdvertsByPriceAsc(OlxAdvert olxAdvert, Model model) {
        List<AllegroAdvert> list = allegroAdvertService.sortByPriceAsc();
        model.addAttribute("list", list);
        return "allegro-adverts";
    }
}
