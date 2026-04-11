package com.kevin.zenvy.backend.user.dto;

import lombok.Builder;

@Builder
public record UserBasicDTO(
        Long id,
        String name,
        String email
) {
}
