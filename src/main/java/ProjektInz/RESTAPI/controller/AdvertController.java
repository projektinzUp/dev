package ProjektInz.RESTAPI.controller;

import ProjektInz.RESTAPI.Service.AdvertService;
import ProjektInz.RESTAPI.restApi.Advert;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Controller
public class AdvertController {
    @Autowired
    private AdvertService advertService;
    @GetMapping("/advert")
    public ResponseEntity<JsonObject> TestAdvertsController()
    {
        JsonObject body = new JsonObject();
        try {
            ArrayList<LinkedHashMap<String,ArrayList<LinkedHashMap<String,Object>>>> response = (ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>) advertService.getAdverts();
            for(LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> iter:response)
            {
                ArrayList<LinkedHashMap<String,Object>> a = iter.get(0);
                LinkedHashMap<String ,Object> b = a.get(0);
                LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> step = iter;

            }

            body.addProperty("Message","advert downloaded");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
