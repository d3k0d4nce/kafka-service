package ru.kishko.restaurantservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.restaurantservice.entities.Restaurant;
import ru.kishko.restaurantservice.mappers.RestaurantMapper;
import ru.kishko.restaurantservice.repositories.RestaurantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantDTO createRestaurant(RestaurantDTO restaurant) {
        restaurantRepository.save(restaurantMapper.map(restaurant));
        return restaurant;
    }

    public RestaurantDTO getRestaurantById(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RuntimeException("There is no restaurant with id: " + restaurantId)
        );

        return restaurantMapper.map(restaurant);
    }

    public List<RestaurantDTO> findTop5RestaurantsByReviewsCount() {
        return restaurantRepository.findTop5RestaurantsByReviewsCount(Limit.of(5)).stream()
                .map(restaurantMapper::map).toList();
    }

    public List<RestaurantDTO> findTop5RestaurantsByReviewsAverageMark() {
        return restaurantRepository.findTop5RestaurantsByReviewsAverageMark(Limit.of(5)).stream()
                .map(restaurantMapper::map).toList();
    }

    public List<RestaurantDTO> findTheWorstRestaurantsByReviewsAverageMark() {
        return restaurantRepository.findTheWorstRestaurantsByReviewsAverageMark().stream()
                .map(restaurantMapper::map).toList();
    }
}
