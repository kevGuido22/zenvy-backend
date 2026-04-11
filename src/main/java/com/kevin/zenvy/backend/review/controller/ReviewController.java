package com.kevin.zenvy.backend.review.controller;

import com.kevin.zenvy.backend.place.dto.PlaceBasicDTO;
import com.kevin.zenvy.backend.review.dto.ReviewCreateDTO;
import com.kevin.zenvy.backend.review.dto.ReviewResponseDTO;
import com.kevin.zenvy.backend.review.model.Review;
import com.kevin.zenvy.backend.review.service.ReviewService;
import com.kevin.zenvy.backend.user.dto.UserBasicDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@Valid @RequestBody ReviewCreateDTO createDto){
         Review review = reviewService.createReview(createDto);

        UserBasicDTO userBasicDTO = UserBasicDTO
                .builder()
                .id(review.getUser().getId())
                .name(review.getUser().getName())
                .email(review.getUser().getEmail())
                .build();

        PlaceBasicDTO placeBasicDTO = PlaceBasicDTO.builder()
                .id(review.getPlace().getId())
                .name(review.getPlace().getName())
                .category(review.getPlace().getCategory())
                .build();

        ReviewResponseDTO responseDTO = ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .place(placeBasicDTO)
                .user(userBasicDTO)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
