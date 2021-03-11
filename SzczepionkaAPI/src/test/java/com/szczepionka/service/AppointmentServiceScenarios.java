package com.szczepionka.service;

import com.szczepionka.entity.Appointment;
import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

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

    @BeforeAll
    void setUp() {
        appointmentRepositoryStub = mock(AppointmentRepository.class);
        patientServiceStub = mock(PatientService.class);
        appointmentService = new AppointmentService(appointmentRepositoryStub, patientServiceStub);
    }

    @Test
    void addAppointment() {
        // Given
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setPesel("00000000000");
        patientDTO.setIdNumber("IDNUMBER");
        patientDTO.setPostalCode("00-000");
        patientDTO.setReferralId("REFERRAL0");
        patientDTO.setEmail("email@email.com");

        Patient patient = new Patient();
        patient.setId(0);
        patient.setPesel("00000000000");
        patient.setIdNumber("IDNUMBER");
        patient.setPostalCode("00-000");
        patient.setReferralId("REFERRAL0");
        patient.setEmail("email@email.com");

        when(patientServiceStub.addPatient(any(PatientDTO.class))).thenReturn(patient);
        when(appointmentRepositoryStub.save(any(Appointment.class))).thenReturn(new Appointment());

        // When
        Appointment result = appointmentService.newAppointment(patientDTO);

        // Then
        then(result).isInstanceOf(Appointment.class);
    }
}