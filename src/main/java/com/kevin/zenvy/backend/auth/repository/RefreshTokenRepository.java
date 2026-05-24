package com.kevin.zenvy.backend.auth.repository;

import com.kevin.zenvy.backend.auth.model.RefreshToken;
import com.kevin.zenvy.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
