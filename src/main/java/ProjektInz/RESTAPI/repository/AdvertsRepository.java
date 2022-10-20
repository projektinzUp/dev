package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertsRepository extends JpaRepository<Advert, String> {

}
