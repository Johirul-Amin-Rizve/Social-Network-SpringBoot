package com.goruslan.socialgeeking.service;

import com.goruslan.socialgeeking.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

}
