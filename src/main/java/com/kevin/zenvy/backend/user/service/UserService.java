package com.kevin.zenvy.backend.user.service;

import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.user.dto.UserCreateDTO;
import com.kevin.zenvy.backend.user.model.User;
import com.kevin.zenvy.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(UserCreateDTO createDTO) {
        Optional<User> currentUser = userRepository.findByEmail(createDTO.email());

        if (currentUser.isPresent()) {
            throw new GeneralException("The email already is taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(createDTO.name());
        user.setEmail(createDTO.email());
        user.setPassword(createDTO.password());

        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new GeneralException("There is an error creating the user: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException("There is not user", HttpStatus.NOT_FOUND));
    }
}
