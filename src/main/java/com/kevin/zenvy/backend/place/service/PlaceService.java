package com.kevin.zenvy.backend.place.service;

import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.place.dto.PlaceCreateDTO;
import com.kevin.zenvy.backend.place.model.Place;
import com.kevin.zenvy.backend.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place addPlace(PlaceCreateDTO placeDTO) {
        Place place = new Place();
        place.setName(placeDTO.name());
        place.setDescription(placeDTO.description());
        place.setCategory(placeDTO.category());
        place.setAddress(placeDTO.address());
        place.setCity(placeDTO.city());
        place.setCountry(placeDTO.country());

        try {
            placeRepository.save(place);
        } catch (Exception ex) {
            throw new GeneralException("Error al agregar un lugar: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return place;
    }

    public List<Place> getPlaces() {
        return placeRepository.findAll();
    }

    public Place getPlace(long id) {
        return placeRepository.findById(id).orElseThrow(() -> new GeneralException("Place not found", HttpStatus.NOT_FOUND));
    }
}
