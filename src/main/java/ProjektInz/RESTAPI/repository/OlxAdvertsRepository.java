package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.AllegroAdvert;
import ProjektInz.RESTAPI.restApi.OlxAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OlxAdvertsRepository extends JpaRepository<OlxAdvert, String> {

    @Query(value = "select * from advert adv where lower(adv.title) like %:keyword%", nativeQuery = true)
    List<OlxAdvert> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from advert adv ORDER BY adv.title DESC", nativeQuery = true)
    List<OlxAdvert> sortByTitleDesc();

    @Query(value = "select * from advert adv ORDER BY adv.title ASC", nativeQuery = true)
    List<OlxAdvert> sortByTitleAsc();

    @Query(value = "select * from advert adv ORDER BY cast(adv.price as float) DESC", nativeQuery = true)
    List<OlxAdvert> sortByPriceDesc();

    @Query(value = "select * from advert adv ORDER BY cast(adv.price as float) ASC", nativeQuery = true)
    List<OlxAdvert> sortByPriceAsc();

}
