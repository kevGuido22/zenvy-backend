package com.kevin.zenvy.backend.image.repository;

import com.kevin.zenvy.backend.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
