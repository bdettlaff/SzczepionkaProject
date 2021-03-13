package com.szczepionka.model;

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

    private String idNumber;

    private String postalCode;

    private String referralId;

    private String email;
}
