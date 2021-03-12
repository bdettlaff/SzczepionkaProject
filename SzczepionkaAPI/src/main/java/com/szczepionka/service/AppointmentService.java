package com.szczepionka.service;

import com.szczepionka.entity.Appointment;
import com.szczepionka.entity.LocationDetails;
import com.szczepionka.entity.Patient;
import com.szczepionka.model.AppointmentStatus;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.AppointmentRepository;
import com.szczepionka.util.VaccinationLocationsFetcher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final VaccinationLocationsFetcher vaccinationLocationsFetcher;
    private final ModelMapper modelMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService, VaccinationLocationsFetcher vaccinationLocationsFetcher, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.vaccinationLocationsFetcher = vaccinationLocationsFetcher;
        this.modelMapper = modelMapper;
    }

    public Appointment newAppointment(PatientDTO patientDTO, long locationId) {
        Patient patient = patientService.addPatient(patientDTO);

        Appointment appointment = Appointment.builder()
                .id(patient.getId())
                .appointmentDate(createAppointmentDate())
                .appointmentTime(createAppointmentTime())
                .appointmentStatus(AppointmentStatus.PLANNED)
                .locationDetails(getLocationDetailsById(locationId))
                .build();

        return appointmentRepository.save(appointment);
    }

    private LocalDate createAppointmentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(getRandomNumberOfDays());
    }

    private int getRandomNumberOfDays() {
        return ThreadLocalRandom.current().nextInt(1, 30);
    }

    private LocalTime createAppointmentTime() {
        LocalTime startingWorkingHours = LocalTime.of(8, 0);
        return startingWorkingHours.plusMinutes(getRandomMinutesOffset());
    }

    private int getRandomMinutesOffset() {
        return 20 * ThreadLocalRandom.current().nextInt(0, 24);
    }

    private LocationDetails getLocationDetailsById(long id) {
        return modelMapper.map(vaccinationLocationsFetcher.getById(id), LocationDetails.class);
    }
}