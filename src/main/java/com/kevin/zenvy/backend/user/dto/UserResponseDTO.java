package com.kevin.zenvy.backend.user.dto;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        String name,
        String email
) {
}
