package com.szczepionka.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class LocationDetails {

    private String city;

    private String postalCode;

    private String address;

    private String locationName;
}
