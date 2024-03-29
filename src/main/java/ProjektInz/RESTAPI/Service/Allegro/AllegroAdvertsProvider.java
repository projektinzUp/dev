package ProjektInz.RESTAPI.Service.Allegro;

import ProjektInz.RESTAPI.repository.AllegroAdvertsRepository;
import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
public class AllegroAdvertsProvider {
    @Autowired
    private AllegroAdvertService allegroAdvertService;
    @Autowired
    private AllegroAdvertsRepository advertsRepository;
    private List<AllegroAdvert> advertList = new ArrayList<>();

    public void createAdvertObject() {
        try {
            log.info("Creating allegro advert object");
            ArrayList<LinkedHashMap<String, Object>> body = (ArrayList<LinkedHashMap<String, Object>>) allegroAdvertService.getAdverts();
            for (LinkedHashMap<String, Object> iter : body) {
                AllegroAdvert allegroAdvert = new AllegroAdvert(iter);
                advertList.add(allegroAdvert);
            }

            advertsRepository.saveAll(advertList);
            log.info("Allegro advert successfully saved into database");
        } catch (Exception e) {
            log.error("Error occurred when creating allegro advert object, message: " + e.getMessage());
        }
    }
}
