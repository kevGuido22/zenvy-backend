package com.kevin.zenvy.backend.image.service;

import com.kevin.zenvy.backend.common.storage.StorageService;
import com.kevin.zenvy.backend.common.storage.TestStorageService;
import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.image.dto.ImageResponseDTO;
import com.kevin.zenvy.backend.image.model.Image;
import com.kevin.zenvy.backend.image.repository.ImageRepository;
import com.kevin.zenvy.backend.place.model.Place;
import com.kevin.zenvy.backend.place.repository.PlaceRepository;
import com.kevin.zenvy.backend.review.model.Review;
import com.kevin.zenvy.backend.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final StorageService storageService;
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public ImageResponseDTO upload(MultipartFile file, Long reviewId, Long placeId){
        if(file == null || file.isEmpty()){
            throw new GeneralException("File is required", HttpStatus.BAD_REQUEST);
        }

        if((reviewId == null && placeId == null) || (reviewId != null && placeId != null)){
            throw new GeneralException("Image must belong to a review or a place", HttpStatus.BAD_REQUEST);
        }

        String fileKey = UUID.randomUUID() + "-" + file.getOriginalFilename();

        String imageUrl = storageService.upload(file, fileKey);

        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setFileKey(fileKey);

        if(reviewId != null){
            Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new GeneralException("Review not found", HttpStatus.NOT_FOUND));
            image.setReview(review);
        }else{
            Place place = placeRepository.findById(placeId).orElseThrow(() -> new GeneralException("Place not found", HttpStatus.NOT_FOUND));
            image.setPlace(place);
        }

        imageRepository.save(image);

        ImageResponseDTO imageDTO = ImageResponseDTO.builder()
                .id(image.getId())
                .fileKey(fileKey)
                .imageUrl(imageUrl)
                .build();

        return imageDTO;
    }
}
