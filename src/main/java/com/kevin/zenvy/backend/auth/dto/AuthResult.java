package com.kevin.zenvy.backend.auth.dto;

import com.kevin.zenvy.backend.user.dto.UserBasicDTO;

public record AuthResult(String token, Long expiresIn, String refreshToken, UserBasicDTO user) {
}
