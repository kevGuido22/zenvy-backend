package com.kevin.zenvy.backend.place.mapper;

import com.kevin.zenvy.backend.image.dto.ImageResponseDTO;
import com.kevin.zenvy.backend.place.dto.PlaceResponseDTO;
import com.kevin.zenvy.backend.place.model.Place;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceMapper {
    public PlaceResponseDTO toDTO(Place place){
        List<ImageResponseDTO> images = place.getImages()
                .stream()
                .map(image -> ImageResponseDTO.builder()
                        .id(image.getId())
                        .imageUrl(image.getImageUrl())
                        .fileKey(image.getFileKey())
                        .build())
                .toList();

        return PlaceResponseDTO.builder()
                .id(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .category(place.getCategory())
                .address(place.getAddress())
                .city(place.getCity())
                .country(place.getCountry())
                .images(images)
                .build();
    }
}
