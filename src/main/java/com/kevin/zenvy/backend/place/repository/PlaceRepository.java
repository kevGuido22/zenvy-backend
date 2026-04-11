package com.kevin.zenvy.backend.place.repository;

import com.kevin.zenvy.backend.place.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
