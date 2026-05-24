package com.kevin.zenvy.backend.auth.controller;

import com.kevin.zenvy.backend.auth.dto.*;
import com.kevin.zenvy.backend.auth.service.AuthService;
import com.kevin.zenvy.backend.exception.GeneralException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Value("${security.jwt.refresh-expiration-time}")
    private Long refreshTokenDurationMs;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok().body(authService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){

        AuthResult authResult = authService.authenticate(loginDTO);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", authResult.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(refreshTokenDurationMs / 1000)
                .sameSite("Lax")
                .build();


        LoginResponseDTO responseBody = LoginResponseDTO.builder()
                .token(authResult.token())
                .expiresIn(authResult.expiresIn())
                .user(authResult.user())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(responseBody);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request){

        Cookie cookie = WebUtils.getCookie(
                request,
                "refreshToken"
        );

        if (cookie == null){
            throw new GeneralException(
                    "Refresh token not found",
                    HttpStatus.UNAUTHORIZED
            );
        }

        LoginResponseDTO response = authService.refreshToken(cookie.getValue());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        authService.logout(request);

        ResponseCookie deleteCookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                deleteCookie.toString()
        );
        return ResponseEntity.ok().build();
    }
}
