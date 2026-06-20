package com.kevin.zenvy.backend.review.mapper;

import com.kevin.zenvy.backend.image.dto.ImageResponseDTO;
import com.kevin.zenvy.backend.place.dto.PlaceBasicDTO;
import com.kevin.zenvy.backend.review.dto.ReviewResponseDTO;
import com.kevin.zenvy.backend.review.model.Review;
import com.kevin.zenvy.backend.user.dto.UserBasicDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {
    public ReviewResponseDTO toDTO(Review review){
        UserBasicDTO user = UserBasicDTO
                .builder()
                .id(review.getUser().getId())
                .name(review.getUser().getName())
                .email(review.getUser().getEmail())
                .build();

        PlaceBasicDTO place = PlaceBasicDTO
                .builder()
                .id(review.getPlace().getId())
                .name(review.getPlace().getName())
                .category(review.getPlace().getCategory())
                .build();

        List<ImageResponseDTO> images = review.getImages()
                .stream()
                .map(image -> ImageResponseDTO.builder()
                        .id(image.getId())
                        .imageUrl(image.getImageUrl())
                        .fileKey(image.getFileKey())
                        .build())
                .toList();

        return ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .user(user)
                .place(place)
                .images(images)
                .build();
    }
}
