package com.kevin.zenvy.backend.auth.dto;

import java.util.List;

public record MeDTO(
        Long id,
        String email,
        List<String> roles
) {
}
