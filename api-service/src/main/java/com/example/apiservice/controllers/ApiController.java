package com.example.apiservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kishko.dtoservice.dtos.RestaurantDTO;
import ru.kishko.dtoservice.dtos.ReviewDTO;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    @Value("${kafka.topic.review}")
    private String reviewTopic;

    @Value("${kafka.topic.restaurant}")
    private String restaurantTopic;

    private final WebClient webClient;
    private final KafkaTemplate<Integer, ReviewDTO> reviewKafkaTemplate;
    private final KafkaTemplate<Integer, RestaurantDTO> restaurantKafkaTemplate;

    @PostMapping("/create/review")
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO review) {
        reviewKafkaTemplate.send(reviewTopic, review);
        return new ResponseEntity<>("Review created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(
                webClient.get().uri("/reviews/" + reviewId).retrieve().bodyToMono(ReviewDTO.class).block(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create/restaurant")
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantDTO restaurant) {
        restaurantKafkaTemplate.send(restaurantTopic, restaurant);
        return new ResponseEntity<>("Restaurant created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable("restaurantId") Long restaurantId) {
        return new ResponseEntity<>(
                webClient.get().uri("/restaurants/" + restaurantId).retrieve().bodyToMono(RestaurantDTO.class).block(),
                HttpStatus.OK
        );
    }

    @GetMapping("/top/count")
    public ResponseEntity<List<RestaurantDTO>> findTop5RestaurantsByReviewsCount() {
        return new ResponseEntity<>(
                webClient.get().uri("/restaurants/top/count").retrieve().bodyToFlux(RestaurantDTO.class).collectList().block(),
                HttpStatus.OK
        );
    }

    @GetMapping("/top/average")
    public ResponseEntity<List<RestaurantDTO>> findTop5RestaurantsByReviewsAverageMark() {
        return new ResponseEntity<>(
                webClient.get().uri("/restaurants/top/average").retrieve().bodyToFlux(RestaurantDTO.class).collectList().block(),
                HttpStatus.OK
        );
    }

    @GetMapping("/worst/average")
    public ResponseEntity<List<RestaurantDTO>> findTheWorstRestaurantsByReviewsAverageMark() {
        return new ResponseEntity<>(
                webClient.get().uri("/restaurants/worst/average").retrieve().bodyToFlux(RestaurantDTO.class).collectList().block(),
                HttpStatus.OK
        );
    }

}
