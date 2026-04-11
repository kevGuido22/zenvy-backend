package com.kevin.zenvy.backend.image.dto;

import lombok.Builder;

@Builder
public record ImageResponseDTO(
    Long id,
    String imageUrl,
    String fileKey
) {
}
