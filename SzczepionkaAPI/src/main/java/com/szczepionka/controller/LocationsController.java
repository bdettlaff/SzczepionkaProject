package com.szczepionka.controller;

import com.szczepionka.model.VaccinationLocation;
import com.szczepionka.service.LocationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationsController {

    private LocationsService locationsService;

    public LocationsController(LocationsService locationsService) {
        this.locationsService = locationsService;
    }

    @GetMapping("/location/{zipcode}")
    public List<VaccinationLocation> getVaccinationLocationByZipcode(@PathVariable String zipcode) {
        return locationsService.getNearestLocationsByZipcode(3, zipcode);
    }
}
