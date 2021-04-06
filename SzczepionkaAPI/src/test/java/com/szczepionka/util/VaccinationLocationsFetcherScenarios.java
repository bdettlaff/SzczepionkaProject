package com.szczepionka.util;

import com.szczepionka.model.VaccinationLocation;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

class VaccinationLocationsFetcherScenarios {

    private static final int NUMBER_OF_ENTRIES_IN_MOCK_VACCINATION_LOCATIONS_JSON_FILE = 8;

    @Test
    void loadJson() {
        // Given
        VaccinationLocationsFetcher vaccinationLocationsFetcher = new VaccinationLocationsFetcher("src/main/resources/vaccination_locations.json");

        // When
        Map<Long, VaccinationLocation> vaccinationLocations = vaccinationLocationsFetcher.getAll();

        // Then
        then(vaccinationLocations.size()).isEqualTo(NUMBER_OF_ENTRIES_IN_MOCK_VACCINATION_LOCATIONS_JSON_FILE);
    }
}
