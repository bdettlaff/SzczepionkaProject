package com.szczepionka.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate appointmentDate;

    @JsonFormat(pattern="HH:mm")
    private LocalTime appointmentTime;

    private String appointmentLocationCity;

    private String appointmentLocationPostalCode;

    private String appointmentLocationAddress;

    private String appointmentLocationName;

}
