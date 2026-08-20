package com.kevin.zenvy.backend.place.dto;

import com.kevin.zenvy.backend.image.dto.ImageResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record PlaceResponseDTO(
        Long id,
        String name,
        String description,
        String category,
        String address,
        String city,
        String country,
        List<ImageResponseDTO> images
) {
}
