package com.kevin.zenvy.backend.place.dto;

import lombok.Builder;

@Builder
public record PlaceResponseDTO(
        String name,
        String description,
        String category,
        String address,
        String city,
        String country
) {
}
