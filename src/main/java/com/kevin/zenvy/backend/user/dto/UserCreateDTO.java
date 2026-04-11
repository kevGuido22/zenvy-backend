package com.kevin.zenvy.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank(message = "The name is required")
        String name,

        @NotBlank(message = "The email is required")
        @Email(message = "The email format is not valid")
        String email,

        @NotBlank(message = "The password is required")
        @Size(min = 8, message = "The password must be at least 8 characters long")
        String password
        ) { }
