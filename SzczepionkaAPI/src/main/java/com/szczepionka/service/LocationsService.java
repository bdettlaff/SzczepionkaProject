package com.szczepionka.service;

import com.szczepionka.model.VaccinationLocation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationsService {

    List<VaccinationLocation> getNearestLocationsByZipcode(int quantityOfNearestLocations, String zipcode);
}
