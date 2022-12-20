package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.AllegroAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllegroAdvertsRepository extends JpaRepository<AllegroAdvert, String> {
}
