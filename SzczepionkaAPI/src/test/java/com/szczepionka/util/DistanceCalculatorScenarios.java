package com.szczepionka.util;

import com.szczepionka.model.Coordinates;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

class DistanceCalculatorScenarios {

    @Test
    void calculateDistance() {
        // Given
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Coordinates srcCoordinates = new Coordinates(BigDecimal.valueOf(51.78840000), BigDecimal.valueOf(19.43070000));
        Coordinates destCoordinates = new Coordinates(BigDecimal.valueOf(51.784550), BigDecimal.valueOf(19.448190));

        // When
        BigDecimal distanceInMeters = distanceCalculator.calculateDistanceInMeters(srcCoordinates, destCoordinates);

        // Then
        then(distanceInMeters.toPlainString()).isEqualTo(BigDecimal.valueOf(1277).toPlainString());
    }

    @Test
    void calculateHugeDistance() {
        // Given
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Coordinates srcCoordinates = new Coordinates(BigDecimal.valueOf(51.78840000), BigDecimal.valueOf(19.43070000));
        Coordinates destCoordinates = new Coordinates(BigDecimal.valueOf(51.5), BigDecimal.valueOf(19.0));

        // When
        BigDecimal distanceInMeters = distanceCalculator.calculateDistanceInMeters(srcCoordinates, destCoordinates);

        // Then
        then(distanceInMeters.toPlainString()).isEqualTo(BigDecimal.valueOf(43720).toPlainString());
    }

}