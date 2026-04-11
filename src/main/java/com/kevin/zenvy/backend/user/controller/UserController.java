package com.kevin.zenvy.backend.user.controller;

import com.kevin.zenvy.backend.user.dto.UserCreateDTO;
import com.kevin.zenvy.backend.user.dto.UserResponseDTO;
import com.kevin.zenvy.backend.user.model.User;
import com.kevin.zenvy.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO createDTO){
        User user = userService.addUser(createDTO);

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok().body(userResponseDTO);
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email){
        User user = userService.getUserByEmail(email);
        UserResponseDTO userResponse = UserResponseDTO
                .builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();

        return ResponseEntity.ok().body(userResponse);
    }
}
