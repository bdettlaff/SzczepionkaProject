package com.szczepionka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AppointmentDetailsDTO {

    private String patientReferralId;

    private VaccinationBrandt vaccinationBrandt;

    private AppointmentStatus appointmentStatus;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String appointmentLocationCity;

    private String appointmentLocationPostalCode;

    private String appointmentLocationAddress;

    private String appointmentLocationName;

}
