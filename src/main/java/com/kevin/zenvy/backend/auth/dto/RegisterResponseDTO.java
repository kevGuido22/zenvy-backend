package com.kevin.zenvy.backend.auth.dto;

import lombok.Builder;

@Builder
public record RegisterResponseDTO(
        String email,
        Long id
) {
}
