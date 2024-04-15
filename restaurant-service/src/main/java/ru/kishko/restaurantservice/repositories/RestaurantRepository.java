package ru.kishko.restaurantservice.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kishko.restaurantservice.entities.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r, COUNT(rv) as review_count FROM Restaurant r JOIN r.reviews rv GROUP BY r.id ORDER BY review_count DESC")
    List<Restaurant> findTop5RestaurantsByReviewsCount(Limit of);

    @Query("SELECT r, AVG(rv.mark) as review_avg FROM Restaurant r JOIN r.reviews rv GROUP BY r.id ORDER BY review_avg DESC")
    List<Restaurant> findTop5RestaurantsByReviewsAverageMark(Limit of);

    @Query("SELECT r, AVG(rv.mark) as review_avg FROM Restaurant r JOIN r.reviews rv GROUP BY r " +
            "HAVING AVG(rv.mark) = (SELECT MIN(avg_mark) FROM (SELECT AVG(rv.mark) as avg_mark " +
            "FROM Restaurant r JOIN r.reviews rv GROUP BY r) AS avg_marks)")
    List<Restaurant> findTheWorstRestaurantsByReviewsAverageMark();

}
