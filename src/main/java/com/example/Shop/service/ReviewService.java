package com.example.Shop.service;

import com.example.Shop.dto.ReviewRequestDto;
import com.example.Shop.dto.ReviewResposeDto;
import com.example.Shop.entity.Review;

import java.util.List;

public interface ReviewService {
    ReviewResposeDto create(ReviewRequestDto dto);
    ReviewResposeDto getById(Long id);
    List< ReviewResposeDto > getAllReviews();
    void delete(Long id);
    default ReviewResposeDto toDto(Review review) {
        return new ReviewResposeDto(
                review.getId(),
                review.getUser().getUsername(),
                review.getProduct().getName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedDate()
        );
    }
}
