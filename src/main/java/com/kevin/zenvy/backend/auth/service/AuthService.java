package com.kevin.zenvy.backend.auth.service;

import com.kevin.zenvy.backend.auth.dto.*;
import com.kevin.zenvy.backend.auth.model.RefreshToken;
import com.kevin.zenvy.backend.auth.repository.RefreshTokenRepository;
import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.security.service.JwtService;
import com.kevin.zenvy.backend.user.dto.UserBasicDTO;
import com.kevin.zenvy.backend.user.model.CustomUserDetails;
import com.kevin.zenvy.backend.user.model.User;
import com.kevin.zenvy.backend.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, Object> register(RegisterDTO registerDto) {
        boolean existEmail = userRepository.existsByEmail(registerDto.email());
        if (existEmail) {
            throw new GeneralException("Email already taken", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(registerDto.name());
        user.setEmail(registerDto.email());
        user.setPassword(passwordEncoder.encode(registerDto.password()));

        User saved = userRepository.save(user);

        RegisterResponseDTO registerResponseDTO = RegisterResponseDTO.builder()
                .email(saved.getEmail())
                .id(saved.getId())
                .build();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "usuario registrado correctamente");
        response.put("statusCode", 201);
        response.put("data", registerResponseDTO);

        return response;
    }

    @Transactional
    public AuthResult authenticate(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        User user = userRepository.findByEmail(input.email())
                .orElseThrow();

        UserBasicDTO userBasicResponse = UserBasicDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .id(user.getId())
                .build();

        String token = jwtService.generateToken(new CustomUserDetails(user));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);




        return new AuthResult(token, jwtService.getExpirationTime(), refreshToken.getToken(), userBasicResponse);
    }

    @Transactional
    public LoginResponseDTO refreshToken(String requestToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestToken).orElseThrow(() -> new GeneralException("RefreshToken is not valid", HttpStatus.UNAUTHORIZED));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new GeneralException("Refresh token is expired", HttpStatus.UNAUTHORIZED);
        }

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(new CustomUserDetails(user));

        return LoginResponseDTO.builder()
                .token(newAccessToken)
                .expiresIn(jwtService.getExpirationTime()).build();

    }

    @Transactional
    public void logout(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "refreshToken");

        if (cookie == null) {
            throw new GeneralException(
                    "Refresh token not found",
                    HttpStatus.UNAUTHORIZED
            );
        }

        String refreshToken = cookie.getValue();

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new GeneralException(
                        "Token is not valid",
                        HttpStatus.UNAUTHORIZED
                ));

        refreshTokenRepository.delete(token);
    }
}
