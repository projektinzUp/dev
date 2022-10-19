package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.restApi.Advert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AdvertsProvider {
    @Autowired
    private AdvertService advertService;
    private  List<Advert> adverts = new ArrayList<>();
    public List<Advert> createAdvertObject() {
        try {
            ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>> response = (ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>) advertService.getAdverts();
            for (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> iter : response) {
                Map<String, Object> testMap = new HashMap<>();
                for (String key : iter.keySet()) {

                    testMap.put(key, iter.get(key));
                }
               //TODO null na stringa w mapce
                Advert advert = new Advert(testMap);

                adverts.add(advert);
            }
            return adverts;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

