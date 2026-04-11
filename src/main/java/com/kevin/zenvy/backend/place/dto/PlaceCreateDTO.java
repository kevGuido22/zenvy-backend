package com.kevin.zenvy.backend.place.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceCreateDTO(
        @NotBlank(message = "The name is required")
        String name,
        String description,
        String category,
        @NotBlank(message = "The address is required")
        String address,
        @NotBlank(message = "The city is required")
        String city,
        @NotBlank(message = "The country is required")
        String country
) {}
