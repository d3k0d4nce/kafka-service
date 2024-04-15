package ru.kishko.restaurantservice.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.dtoservice.dtos.ReviewDTO;
import ru.kishko.restaurantservice.entities.Restaurant;
import ru.kishko.restaurantservice.entities.Review;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {

    private final ReviewMapper reviewMapper;

    public RestaurantDTO map(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .reviews(mapReviews(restaurant.getReviews()))
                .build();
    }

    public Restaurant map(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .name(restaurantDTO.getName())
                .reviews(mapReviewsDTO(restaurantDTO.getReviews()))
                .build();
    }

    private List<ReviewDTO> mapReviews(List<Review> reviews) {
        return reviews.stream()
                .map(reviewMapper::map)
                .collect(Collectors.toList());
    }

    private List<Review> mapReviewsDTO(List<ReviewDTO> reviews) {
        return reviews.stream()
                .map(reviewMapper::map)
                .collect(Collectors.toList());
    }
}

