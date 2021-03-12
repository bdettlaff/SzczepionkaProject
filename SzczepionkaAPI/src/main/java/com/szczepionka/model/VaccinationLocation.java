package com.szczepionka.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VaccinationLocation {

    private long id;

    private String country;

    private String city;

    @SerializedName("postal code")
    private String postalCode;

    private String address;

    private String name;

    @SerializedName("number of available vaccines")
    private int numberOfAvailableVaccines;

    @SerializedName("vaccine name")
    private String vaccineName;
}
