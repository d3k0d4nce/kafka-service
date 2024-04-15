package ru.kishko.restaurantservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.restaurantservice.services.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurant) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurantById(restaurantId), HttpStatus.OK);
    }

    @GetMapping("/top/count")
    public ResponseEntity<List<RestaurantDTO>> findTop5RestaurantsByReviewsCount() {
        return new ResponseEntity<>(restaurantService.findTop5RestaurantsByReviewsCount(), HttpStatus.OK);
    }

    @GetMapping("/top/average")
    public ResponseEntity<List<RestaurantDTO>> findTop5RestaurantsByReviewsAverageMark() {
        return new ResponseEntity<>(restaurantService.findTop5RestaurantsByReviewsAverageMark(), HttpStatus.OK);
    }

    @GetMapping("/worst/average")
    public ResponseEntity<List<RestaurantDTO>> findTheWorstRestaurantsByReviewsAverageMark() {
        return new ResponseEntity<>(restaurantService.findTheWorstRestaurantsByReviewsAverageMark(), HttpStatus.OK);
    }

}
