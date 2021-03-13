package com.szczepionka;

import com.szczepionka.entity.Appointment;
import com.szczepionka.entity.LocationDetails;
import com.szczepionka.entity.Patient;
import com.szczepionka.model.AppointmentStatus;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.model.VaccinationLocation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ObjectMother {

    public static PatientDTO defaultPatientDto() {
        return PatientDTO.builder()
                .pesel("00000000000")
                .idNumber("IDNUMBER")
                .postalCode("00-000")
                .referralId("REFERRAL0")
                .email("email@email.com")
                .build();
    }

    public static Patient defaultPateient() {
        return Patient.builder()
                .pesel("00000000000")
                .idNumber("IDNUMBER")
                .postalCode("00-000")
                .referralId("REFERRAL0")
                .email("email@email.com")
                .build();
    }

    public static Appointment defaultAppointment() {
        return Appointment.builder()
                .id(1)
                .patientId(1)
                .appointmentDate(LocalDate.now().plusDays(1))
                .appointmentTime(LocalTime.of(10, 0))
                .appointmentStatus(AppointmentStatus.PLANNED)
                .locationDetails(new LocationDetails("Lodz", "91-053", "Bazarowa 9", "Centra Medyczne Medyceusz"))
                .build();
    }

    public static VaccinationLocation defaultVaccinationLocation() {
        return VaccinationLocation.builder()
                .id(1)
                .country("Poland")
                .city("Lodz")
                .postalCode("91-053")
                .address("Bazarowa 9")
                .name("Centra Medyczne Medyceusz")
                .numberOfAvailableVaccines(5)
                .vaccineName("Moderna")
                .build();
    }
}
