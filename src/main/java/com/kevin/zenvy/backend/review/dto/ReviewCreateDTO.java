package com.kevin.zenvy.backend.review.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateDTO(
//        @NotNull(message = "The userId field is required")
//        Long userId,

        @NotNull(message = "The placeId field is required")
        Long placeId,

        @Min(value = 1, message = "The min value for rating is 1")
        @Max(value = 5, message = "The max value for rating is 5")
        @NotNull(message = "Then rating field is required")
        Integer rating,

        String comment
) {
}
