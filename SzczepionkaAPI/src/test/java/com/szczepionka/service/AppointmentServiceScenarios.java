package com.szczepionka.service;

import com.szczepionka.ObjectMother;
import com.szczepionka.entity.Appointment;
import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.model.VaccinationLocation;
import com.szczepionka.repository.AppointmentRepository;
import com.szczepionka.util.VaccinationLocationsFetcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentServiceScenarios {

    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepositoryStub;
    @Mock
    private PatientService patientServiceStub;
    @Mock
    private VaccinationLocationsFetcher vaccinationLocationsFetcherStub;
    @Mock
    private EmailService emailServiceStub;

    @BeforeAll
    void setUp() {
        appointmentRepositoryStub = mock(AppointmentRepository.class);
        patientServiceStub = mock(PatientService.class);
        vaccinationLocationsFetcherStub = mock(VaccinationLocationsFetcher.class);
        ModelMapper modelMapper = new ModelMapper();
        appointmentService = new AppointmentService(appointmentRepositoryStub, patientServiceStub, vaccinationLocationsFetcherStub, modelMapper,emailServiceStub);
    }

    @Test
    void addAppointment() throws MessagingException {
        // Given
        PatientDTO patientDTO = ObjectMother.defaultPatientDto();
        Patient patient = ObjectMother.defaultPateient();
        Appointment appointment = ObjectMother.defaultAppointment();
        VaccinationLocation vaccinationLocation = ObjectMother.defaultVaccinationLocation();

        when(patientServiceStub.addPatient(any(PatientDTO.class))).thenReturn(patient);
        when(appointmentRepositoryStub.save(any(Appointment.class))).thenReturn(appointment);
        when(vaccinationLocationsFetcherStub.getById(1)).thenReturn(vaccinationLocation);

        // When
        Appointment result = appointmentService.newAppointment(patientDTO, 1);

        // Then
        then(result).isInstanceOf(Appointment.class);
        then(result.getId()).isEqualTo(1);
        then(result.getAppointmentDate()).isEqualTo(LocalDate.now().plusDays(1));
        then(result.getAppointmentTime()).isEqualTo(LocalTime.of(10, 0));
        then(result.getLocationDetails().getCity()).isEqualTo(vaccinationLocation.getCity());
        then(result.getLocationDetails().getPostalCode()).isEqualTo(vaccinationLocation.getPostalCode());
        then(result.getLocationDetails().getAddress()).isEqualTo(vaccinationLocation.getAddress());
        then(result.getLocationDetails().getLocationName()).isEqualTo(vaccinationLocation.getName());
    }
}