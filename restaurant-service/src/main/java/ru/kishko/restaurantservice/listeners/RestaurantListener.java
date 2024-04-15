package ru.kishko.restaurantservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.restaurantservice.services.RestaurantService;

@Component
@RequiredArgsConstructor
public class RestaurantListener {

    private final RestaurantService restaurantService;

    @KafkaListener(topics = "${kafka.topic.restaurant}", groupId = "${spring.kafka.restaurant-group-id}",
            containerFactory = "restaurantKafkaListenerContainerFactory")
    public void createRestaurant(RestaurantDTO restaurantDTO){
        restaurantService.createRestaurant(restaurantDTO);
    }

}
