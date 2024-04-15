package ru.kishko.restaurantservice.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kishko.dtoservice.dtos.ReviewDTO;
import ru.kishko.restaurantservice.services.ReviewService;

@Component
@RequiredArgsConstructor
public class ReviewListener {

    private final ReviewService reviewService;

    @KafkaListener(topics = "${kafka.topic.review}", groupId = "${spring.kafka.review-group-id}",
            containerFactory = "reviewKafkaListenerContainerFactory")
    public void createReview(ReviewDTO reviewDTO){
        reviewService.createReview(reviewDTO);
    }

}
