package com.szczepionka.service;

import com.szczepionka.model.Coordinates;
import com.szczepionka.model.VaccinationLocation;
import com.szczepionka.util.DistanceCalculator;
import com.szczepionka.util.VaccinationLocationsFetcher;
import com.szczepionka.util.ZipcodeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationsServiceScenarios {

    @Mock
    ZipcodeConverter zipcodeConverterStub;

    private LocationsService locationsService;

    @BeforeEach
    void setUp() {
        zipcodeConverterStub = mock(ZipcodeConverter.class);
        this.locationsService = new LocationsServiceImpl(zipcodeConverterStub, new VaccinationLocationsFetcher(), new DistanceCalculator());
    }

    @Test
    void getThreeNearestLocationsByZipcode() {
        // Given
        int quantityOfNearestLocations = 3;
        when(zipcodeConverterStub.zipcodeToCoordinates(any())).thenReturn(new Coordinates(BigDecimal.valueOf(51.7884), BigDecimal.valueOf(19.4307)));

        // When
        List<VaccinationLocation> result = locationsService.getNearestLocationsByZipcode(quantityOfNearestLocations, "91-041");

        // Then
        then(result).hasSize(quantityOfNearestLocations);
    }
}