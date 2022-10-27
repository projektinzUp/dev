package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.OlxAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OlxAdvertsRepository extends JpaRepository<OlxAdvert, String> {

    @Query(value = "select * from advert adv where adv.title like %:keyword%", nativeQuery = true)
    List<OlxAdvert> findByKeyword(@Param("keyword") String keyword);

}
