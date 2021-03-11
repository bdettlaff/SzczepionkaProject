package com.szczepionka.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {

    private String pesel;

    private String idNumber;

    private String postalCode;

    private String referralId;

    private String email;
}
