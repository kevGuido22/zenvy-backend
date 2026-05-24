package com.kevin.zenvy.backend.auth.service;

import com.kevin.zenvy.backend.auth.model.RefreshToken;
import com.kevin.zenvy.backend.auth.repository.RefreshTokenRepository;
import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.user.model.User;
import com.kevin.zenvy.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.jwt.refresh-expiration-time}")
    private Long refreshTokenDurationMs;
    
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(User user){
        refreshTokenRepository.deleteByUser(user);

        RefreshToken token = new RefreshToken();

        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(token);
    }

    public void verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new GeneralException("Refresh token expired. Please login again", HttpStatus.UNAUTHORIZED);
        }
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }


}
