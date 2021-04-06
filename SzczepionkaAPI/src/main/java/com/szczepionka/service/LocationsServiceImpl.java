package com.szczepionka.service;

import com.szczepionka.model.Coordinates;
import com.szczepionka.model.VaccinationLocation;
import com.szczepionka.util.DistanceCalculator;
import com.szczepionka.util.VaccinationLocationsFetcher;
import com.szczepionka.util.ZipcodeConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationsServiceImpl implements LocationsService {

    private final ZipcodeConverter zipcodeConverter;
    private final VaccinationLocationsFetcher vaccinationLocationsFetcher;
    private final DistanceCalculator distanceCalculator;

    public LocationsServiceImpl(ZipcodeConverter zipcodeConverter, VaccinationLocationsFetcher vaccinationLocationsFetcher, DistanceCalculator distanceCalculator) {
        this.zipcodeConverter = zipcodeConverter;
        this.vaccinationLocationsFetcher = vaccinationLocationsFetcher;
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public List<VaccinationLocation> getNearestLocationsByZipcode(int quantityOfNearestLocations, String zipcode) {
        Coordinates coordinates = zipcodeConverter.zipcodeToCoordinates(zipcode);

        Map<Object, VaccinationLocation> distanceMap = vaccinationLocationsFetcher.getAll().values().stream()
                .collect(Collectors.toMap(
                        entry -> distanceCalculator.calculateDistanceInMeters(coordinates, new Coordinates(entry.getLatitude(), entry.getLongitude())),
                        Function.identity()
                ));

        Map<Object, VaccinationLocation> mapSortedByDistanceAsc = new TreeMap<>(distanceMap);
        return mapSortedByDistanceAsc.values().stream()
                .limit(quantityOfNearestLocations)
                .collect(Collectors.toList());
    }
}
