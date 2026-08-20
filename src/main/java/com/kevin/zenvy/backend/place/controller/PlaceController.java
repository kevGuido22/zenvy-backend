package com.kevin.zenvy.backend.place.controller;

import com.kevin.zenvy.backend.place.dto.PlaceCreateDTO;
import com.kevin.zenvy.backend.place.dto.PlaceResponseDTO;
import com.kevin.zenvy.backend.place.model.Place;
import com.kevin.zenvy.backend.place.service.PlaceService;
import com.kevin.zenvy.backend.review.dto.ReviewResponseDTO;
import com.kevin.zenvy.backend.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> addPlace(@Valid @RequestBody PlaceCreateDTO createDTO) {
        Place place = placeService.addPlace(createDTO);

        PlaceResponseDTO placeResponseDTO = PlaceResponseDTO.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .category(place.getCategory())
                .address(place.getAddress())
                .city(place.getCity())
                .country(place.getCountry())
                .build();

        return ResponseEntity.ok(placeResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> getPlaces(){
        List<PlaceResponseDTO> placeList = placeService.getPlaces();

        return ResponseEntity.ok().body(placeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponseDTO> getPlace(@PathVariable Long id){
        return ResponseEntity.ok(placeService.getPlace(id));
    }

    @GetMapping("/{placeId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByPlace(@PathVariable Long placeId){
        return ResponseEntity.ok().body(reviewService.getReviewsByPlace(placeId));
    }
}
