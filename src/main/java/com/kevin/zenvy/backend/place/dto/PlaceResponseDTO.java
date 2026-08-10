package com.kevin.zenvy.backend.place.dto;

import lombok.Builder;

@Builder
public record PlaceResponseDTO(
        Long id,
        String name,
        String description,
        String category,
        String address,
        String city,
        String country
) {
}
