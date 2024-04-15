package ru.kishko.restaurantservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.restaurantservice.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
