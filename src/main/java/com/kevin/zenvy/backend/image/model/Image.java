package com.kevin.zenvy.backend.image.model;

import com.kevin.zenvy.backend.place.model.Place;
import com.kevin.zenvy.backend.review.model.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "file_key", nullable = false)
    private String fileKey;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    private void validateRelation() {
        if (review == null && place == null) {
            throw new IllegalStateException("Image must belong to a review or a place");
        }
        if (review != null && place != null) {
            throw new IllegalStateException("Image cannot belong to both review and place");
        }
    }
}
