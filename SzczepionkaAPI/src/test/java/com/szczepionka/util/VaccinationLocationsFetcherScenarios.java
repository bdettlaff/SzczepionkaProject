package com.szczepionka.util;

import com.szczepionka.model.VaccinationLocation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

class VaccinationLocationsFetcherScenarios {

    private static final int NUMBER_OF_ENTRIES_IN_MOCK_VACCINATION_LOCATIONS_JSON_FILE = 8;

    @Test
    void loadJson() {
        // Given
        VaccinationLocationsFetcher vaccinationLocationsFetcher = new VaccinationLocationsFetcher();

        // When
        List<VaccinationLocation> vaccinationLocations = vaccinationLocationsFetcher.fetchInitialData();

        // Then
        then(vaccinationLocations.size()).isEqualTo(NUMBER_OF_ENTRIES_IN_MOCK_VACCINATION_LOCATIONS_JSON_FILE);
    }
}
