package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.repository.AdvertsRepository;
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
    @Autowired
    private AdvertsRepository advertsRepository;
    private List<Advert> adverts = new ArrayList<>();

    public List<Advert> createAdvertObject() {
        try {
            ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>> response = (ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>) advertService.getAdverts();
            for (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> iter : response) {
                Map<String, Object> testMap = new HashMap<>();
                for (String key : iter.keySet()) {

                    testMap.put(key, iter.get(key));
                }

                nullToStringConverter(testMap);
                Advert advert = new Advert(testMap);

                adverts.add(advert);
                advertsRepository.save(advert);
            }
            return adverts;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void nullToStringConverter(Map<String, Object> map) {
        for (String key : map.keySet()) {
            if (key == "images") {
                List<LinkedHashMap<String, String>> imagesList = (List<LinkedHashMap<String, String>>) map.get("images");
                if (imagesList.size() == 0) {
                    LinkedHashMap<String, String> a = new LinkedHashMap<>();
                    a.put("url", "null");
                    imagesList.add(0, a);
                    map.put(key, imagesList);
                }
            } else if (key == "price") {
                if(map.get("price") == null){
                    LinkedHashMap<String, Object> priceLinked2 = new LinkedHashMap<>();
                    priceLinked2.put("value", 0);
                    map.put(key, priceLinked2);
                }
            }
        }
    }
}

