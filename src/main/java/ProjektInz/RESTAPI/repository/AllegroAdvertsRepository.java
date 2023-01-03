package ProjektInz.RESTAPI.repository;

import ProjektInz.RESTAPI.restApi.Allegro.AllegroAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllegroAdvertsRepository extends JpaRepository<AllegroAdvert, String> {

    @Query(value = "select * from advertallegro aa where lower(aa.title) like %:keyword%", nativeQuery = true)
    List<AllegroAdvert> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from advertallegro aa ORDER BY aa.title DESC", nativeQuery = true)
    List<AllegroAdvert> sortByTitleDesc();

    @Query(value = "select * from advertallegro aa ORDER BY aa.title ASC", nativeQuery = true)
    List<AllegroAdvert> sortByTitleAsc();

    @Query(value = "select * from advertallegro aa ORDER BY cast(aa.price as float) DESC", nativeQuery = true)
    List<AllegroAdvert> sortByPriceDesc();

    @Query(value = "select * from advertallegro aa ORDER BY cast(aa.price as float) ASC", nativeQuery = true)
    List<AllegroAdvert> sortByPriceAsc();

}
