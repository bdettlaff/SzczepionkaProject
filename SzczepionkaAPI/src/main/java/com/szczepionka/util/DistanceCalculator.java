package com.szczepionka.util;

import com.szczepionka.model.Coordinates;

import java.math.BigDecimal;
import java.math.MathContext;

public class DistanceCalculator {

    final int R = 6371; // Radius of the earth
    final int METERS_VALUE = 1000;

    public BigDecimal calculateDistanceInMeters(Coordinates srcCoordinates, Coordinates destCoordinates) {
        double sourceLatitude = srcCoordinates.getLatitude().doubleValue();
        double destinationLatitude = destCoordinates.getLatitude().doubleValue();
        double latDistance = Math.abs(Math.toRadians(destinationLatitude - sourceLatitude));

        double destinationLongitude = destCoordinates.getLongitude().doubleValue();
        double sourceLongitude = srcCoordinates.getLongitude().doubleValue();
        double lonDistance = Math.abs(Math.toRadians(destinationLongitude - sourceLongitude));

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(sourceLatitude)) * Math.cos(Math.toRadians(destinationLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanceInMeters = R * c * METERS_VALUE;
        return BigDecimal.valueOf(distanceInMeters).round(new MathContext(4));
    }
}
