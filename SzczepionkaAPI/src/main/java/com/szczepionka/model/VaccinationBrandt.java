package com.szczepionka.model;

import com.google.gson.annotations.SerializedName;

public enum VaccinationBrandt {
    @SerializedName("AstraZeneca")
    ASTRA_ZENECA,

    @SerializedName("Pfizer")
    PFIZER,

    @SerializedName("Moderna")
    MODERNA;
}