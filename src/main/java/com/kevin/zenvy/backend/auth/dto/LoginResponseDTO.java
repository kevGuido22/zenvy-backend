package com.kevin.zenvy.backend.auth.dto;

import com.kevin.zenvy.backend.auth.model.RefreshToken;
import com.kevin.zenvy.backend.user.dto.UserBasicDTO;
import lombok.Builder;

@Builder
public record LoginResponseDTO(
        String token,
        long expiresIn,
        UserBasicDTO user
) { }
