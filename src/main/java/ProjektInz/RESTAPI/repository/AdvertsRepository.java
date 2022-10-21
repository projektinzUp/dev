package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertsRepository extends JpaRepository<Advert, String> {

}
