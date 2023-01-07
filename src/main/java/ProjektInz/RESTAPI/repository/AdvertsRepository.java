package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertsRepository extends JpaRepository<AllegroAdvert, String> {
    String allAdvertsQuery = "select id, title, url, images, cast(price as float) as price from advertallegro UNION select id, title, url, images, cast(price as float) as price from advert";

    @Query(value = allAdvertsQuery, nativeQuery = true)
    List<AllegroAdvert> findAllAdverts();

    @Query(value = allAdvertsQuery + "select id, title, url, images, cast(price as float) as price from advertallegro where lower(title) like %:keyword% UNION select id, title, url, images, cast(price as float) as price from advert where lower(title) like %:keyword%", nativeQuery = true)
    List<AllegroAdvert> findByKeyword(@Param("keyword") String keyword);

    @Query(value = allAdvertsQuery + " ORDER BY title DESC", nativeQuery = true)
    List<AllegroAdvert> sortByTitleDesc();

    @Query(value = allAdvertsQuery + " ORDER BY title ASC", nativeQuery = true)
    List<AllegroAdvert> sortByTitleAsc();

    @Query(value = allAdvertsQuery + " ORDER BY price DESC", nativeQuery = true)
    List<AllegroAdvert> sortByPriceDesc();

    @Query(value = allAdvertsQuery + " ORDER BY price ASC", nativeQuery = true)
    List<AllegroAdvert> sortByPriceAsc();
}
