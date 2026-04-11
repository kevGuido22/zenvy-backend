package com.kevin.zenvy.backend.image.controller;

import com.kevin.zenvy.backend.image.dto.ImageResponseDTO;
import com.kevin.zenvy.backend.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDTO> upload(
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(required = false) Long reviewId,
            @RequestParam(required = false) Long placeId
    ){
        return ResponseEntity.ok().body(imageService.upload(file, reviewId, placeId));
    }
}
