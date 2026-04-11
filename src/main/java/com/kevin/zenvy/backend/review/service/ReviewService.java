package com.kevin.zenvy.backend.review.service;

import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.place.dto.PlaceBasicDTO;
import com.kevin.zenvy.backend.place.model.Place;
import com.kevin.zenvy.backend.place.repository.PlaceRepository;
import com.kevin.zenvy.backend.review.dto.ReviewCreateDTO;
import com.kevin.zenvy.backend.review.dto.ReviewResponseDTO;
import com.kevin.zenvy.backend.review.mapper.ReviewMapper;
import com.kevin.zenvy.backend.review.model.Review;
import com.kevin.zenvy.backend.review.repository.ReviewRepository;
import com.kevin.zenvy.backend.user.dto.UserBasicDTO;
import com.kevin.zenvy.backend.user.model.User;
import com.kevin.zenvy.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public Review createReview(ReviewCreateDTO createDTO){
        Place place = placeRepository.findById(createDTO.placeId()).orElseThrow(() -> new GeneralException("Place not found", HttpStatus.NOT_FOUND));

        User user = userRepository.findById(createDTO.userId()).orElseThrow(() -> new GeneralException("User not found", HttpStatus.NOT_FOUND));

        boolean existsReview = reviewRepository.existsByUserAndPlace(user, place);

        if(existsReview){
            throw new GeneralException("Review already exists for this user and place", HttpStatus.BAD_REQUEST);
        }

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setRating(createDTO.rating());
        review.setComment(createDTO.comment());

        return reviewRepository.save(review);
    }

    public List<ReviewResponseDTO> getReviewsByPlace(Long placeId){
        placeRepository.findById(placeId).orElseThrow(() -> new GeneralException("Place not found", HttpStatus.NOT_FOUND));

        return reviewRepository
                .findAllByPlace_Id(placeId)
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
}
