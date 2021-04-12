package com.szczepionka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientDTO {

    private String pesel;

    @JsonProperty("identificator")
    private String idNumber;

    @JsonProperty("zipcode")
    private String postalCode;

    @JsonProperty("referral")
    private String referralId;

    private String email;
}
