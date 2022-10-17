package ProjektInz.RESTAPI.controller;

import ProjektInz.RESTAPI.Service.AdvertService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdvertController {
    @Autowired
    private AdvertService advertService;
    @GetMapping("/advert")
    public ResponseEntity<JsonObject> TestAdvertsController()
    {
        JsonObject body = new JsonObject();
        try {
            advertService.getAdverts();
            body.addProperty("Message","advert downloaded");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
