package com.kevin.zenvy.backend.review.dto;

import com.kevin.zenvy.backend.image.dto.ImageResponseDTO;
import com.kevin.zenvy.backend.place.dto.PlaceBasicDTO;
import com.kevin.zenvy.backend.user.dto.UserBasicDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record ReviewResponseDTO(
    Long id,
    Integer rating,
    String comment,
    UserBasicDTO user,
    PlaceBasicDTO place,
    List<ImageResponseDTO> images
) {
}
