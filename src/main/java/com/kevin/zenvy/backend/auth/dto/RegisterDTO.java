package com.kevin.zenvy.backend.auth.dto;

import com.kevin.zenvy.backend.utils.RegexConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "The email is required")
        @Email(message = "The email format is not valid")
        String email,

        @NotBlank(message = "The username is required")
        @Size(min = 5, max = 50, message = "The username must be between 5 and 50 characters")
        String name,

        @NotBlank(message = "The password is required")
        @Size(min = 8, message = "The password must be at least 8 characters long")
        @Pattern(
                regexp = RegexConstant.PASSWORD_REGEX,
                message = "The password must contain one uppercase letter, one lowercase letter, one number and one special character"
        )
        String password
) {
}
