package ProjektInz.RESTAPI.Controllers;

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
public class advertOlxController {
    @Autowired
    OlxAdvertService olxAdvertService;

    @RequestMapping(path = {"olxAdverts"})
    public String home(OlxAdvert olxAdvert, Model model, String keyword) {
        if (keyword != null) {
            List<OlxAdvert> list = olxAdvertService.getByKeyword(keyword);
            model.addAttribute("list", list);
        } else {
            List<OlxAdvert> list = olxAdvertService.findAllAdverts();
            model.addAttribute("list", list);
        }
        return "olx-adverts";
    }

    @RequestMapping(path = {"/olxSortAdvertsDesc"})
    public String olxSortAdvertsDesc(OlxAdvert olxAdvert, Model model) {
        List<OlxAdvert> list = olxAdvertService.sortByTitleDesc();
        model.addAttribute("list", list);
        return "olx-adverts";
    }

    @RequestMapping(path = {"/olxSortAdvertsAsc"})
    public String olxSortAdvertsAsc(OlxAdvert olxAdvert, Model model) {
        List<OlxAdvert> list = olxAdvertService.sortByTitleAsc();
        model.addAttribute("list", list);
        return "olx-adverts";
    }

    @RequestMapping(path = {"/olxSortAdvertsByPriceDesc"})
    public String olxSortAdvertsByPriceDesc(OlxAdvert olxAdvert, Model model) {
        List<OlxAdvert> list = olxAdvertService.sortByPriceDesc();
        model.addAttribute("list", list);
        return "olx-adverts";
    }

    @RequestMapping(path = {"/olxSortAdvertsByPriceAsc"})
    public String olxSortAdvertsByPriceAsc(OlxAdvert olxAdvert, Model model) {
        List<OlxAdvert> list = olxAdvertService.sortByPriceAsc();
        model.addAttribute("list", list);
        return "olx-adverts";
    }

}
