package com.kevin.zenvy.backend.review.repository;

import com.kevin.zenvy.backend.place.model.Place;
import com.kevin.zenvy.backend.review.model.Review;
import com.kevin.zenvy.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndPlace(User user, Place place);

    List<Review> findAllByPlace_Id(Long placeId);
}
