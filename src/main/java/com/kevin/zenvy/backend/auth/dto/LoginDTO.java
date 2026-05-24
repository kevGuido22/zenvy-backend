package com.kevin.zenvy.backend.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Valid
public record LoginDTO(
        @NotBlank(message = "The email is required")
        @Email(message = "The email format is not valid")
        String email,

        @NotBlank(message = "The password is required")
        String password
) {
}