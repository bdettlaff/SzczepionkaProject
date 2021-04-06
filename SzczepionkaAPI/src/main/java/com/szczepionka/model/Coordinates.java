package com.szczepionka.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Coordinates {

    private BigDecimal latitude;
    private BigDecimal longitude;

    private Coordinates() {
        // nothing to do
    }

    public Coordinates(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
