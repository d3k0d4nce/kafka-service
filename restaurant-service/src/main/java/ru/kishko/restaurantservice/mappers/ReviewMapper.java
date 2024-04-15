package ru.kishko.restaurantservice.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.dtoservice.dtos.ReviewDTO;
import ru.kishko.restaurantservice.entities.Restaurant;
import ru.kishko.restaurantservice.entities.Review;
import ru.kishko.restaurantservice.repositories.RestaurantRepository;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final RestaurantRepository restaurantRepository;

    public ReviewDTO map(Review review) {
        return ReviewDTO.builder()
                .description(review.getDescription())
                .mark(review.getMark())
                .restaurantId(review.getRestaurant().getId())
                .build();
    }

    public Review map(ReviewDTO reviewDTO) {
        Restaurant restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return Review.builder()
                .description(reviewDTO.getDescription())
                .mark(reviewDTO.getMark())
                .restaurant(restaurant)
                .build();
    }

}
