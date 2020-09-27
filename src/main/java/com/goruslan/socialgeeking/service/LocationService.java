package com.goruslan.socialgeeking.service;

import com.goruslan.socialgeeking.domain.Location;
import com.goruslan.socialgeeking.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

}
