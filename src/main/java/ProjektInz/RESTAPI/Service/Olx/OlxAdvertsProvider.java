package ProjektInz.RESTAPI.Service.Olx;

import ProjektInz.RESTAPI.repository.OlxAdvertsRepository;
import ProjektInz.RESTAPI.restApi.Olx.OlxAdvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OlxAdvertsProvider {
    @Autowired
    private OlxAdvertService olxAdvertService;
    @Autowired
    private OlxAdvertsRepository olxAdvertsRepository;
    private List<OlxAdvert> olxAdverts = new ArrayList<>();

    public List<OlxAdvert> createAdvertObject() throws Exception {
        try {
            log.info("Starting to download olx adverts");
            ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>> response =
                    (ArrayList<LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>>) olxAdvertService.getAdverts();
            for (LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>> iter : response) {
                Map<String, Object> testMap = new HashMap<>();
                for (String key : iter.keySet()) {

                    testMap.put(key, iter.get(key));
                }

                nullToStringConverter(testMap);
                OlxAdvert olxAdvert = new OlxAdvert(testMap);

                olxAdverts.add(olxAdvert);
                olxAdvertsRepository.save(olxAdvert);
            }
            log.info("Olx adverts successfully saved into database");
            return olxAdverts;

        } catch (Exception e) {
            log.error("An error occurred while processing olx adverts, message: " + e.getMessage());
            throw new Exception("An error occurred while processing olx adverts, message: " + e.getMessage());
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

