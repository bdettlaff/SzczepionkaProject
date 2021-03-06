package com.szczepionka.entity;

import com.sun.istack.NotNull;
import com.szczepionka.model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long patientId;

    @NotNull
    private LocalDate firstAppointmentDate;

    @NotNull
    private LocalTime firstAppointmentTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AppointmentStatus firstAppointmentStatus;

    private LocalDate secondAppointmentDate;

    private LocalTime secondAppointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus secondAppointmentStatus;

    @Embedded
    @NotNull
    private LocationDetails locationDetails;
}
