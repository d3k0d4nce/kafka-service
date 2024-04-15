package ru.kishko.restaurantservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kishko.dtoservice.dtos.ReviewDTO;
import ru.kishko.restaurantservice.entities.Review;
import ru.kishko.restaurantservice.mappers.ReviewMapper;
import ru.kishko.restaurantservice.repositories.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDTO createReview(ReviewDTO review) {
        reviewRepository.save(reviewMapper.map(review));
        return review;
    }

    public ReviewDTO getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("There is no review with id: " + reviewId)
        );

        return reviewMapper.map(review);
    }

}
