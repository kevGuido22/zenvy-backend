package com.kevin.zenvy.backend.place.service;

import com.kevin.zenvy.backend.exception.GeneralException;
import com.kevin.zenvy.backend.place.dto.PlaceCreateDTO;
import com.kevin.zenvy.backend.place.dto.PlaceResponseDTO;
import com.kevin.zenvy.backend.place.mapper.PlaceMapper;
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
    private final PlaceMapper placeMapper;

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

    public List<PlaceResponseDTO> getPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(placeMapper::toDTO)
                .toList();
    }

    public PlaceResponseDTO getPlace(long id) {
        return placeRepository.findById(id)
                .map(placeMapper::toDTO)
                .orElseThrow(() -> new GeneralException("Place not found", HttpStatus.NOT_FOUND));
    }
}
