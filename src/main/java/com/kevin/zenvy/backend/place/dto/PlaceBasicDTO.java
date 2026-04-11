package com.kevin.zenvy.backend.place.dto;

import lombok.Builder;

@Builder
public record PlaceBasicDTO(
        Long id,
        String name,
        String category
) {
}
