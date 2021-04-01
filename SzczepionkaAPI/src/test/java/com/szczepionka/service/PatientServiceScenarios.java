package com.szczepionka.service;

import com.szczepionka.ObjectMother;
import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.PatientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientServiceScenarios {

    private PatientService patientService;

    @Mock
    private PatientRepository patientRepositoryStub;

    @BeforeAll
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        patientRepositoryStub = mock(PatientRepository.class);
        this.patientService = new PatientService(patientRepositoryStub, modelMapper);
    }

    @Test
    void addPatient() {
        // Given
        PatientDTO patientDTO = ObjectMother.defaultPatientDto();
        Patient patient = ObjectMother.defaultPateient();

        when(patientRepositoryStub.save(any(Patient.class))).thenReturn(patient);

        // When
        Patient result = patientService.addPatient(patientDTO);

        // Then
        then(result.getPesel()).isEqualTo(patientDTO.getPesel());
        then(result.getIdNumber()).isEqualTo(patientDTO.getIdNumber());
        then(result.getPostalCode()).isEqualTo(patientDTO.getPostalCode());
        then(result.getReferralId()).isEqualTo(patientDTO.getReferralId());
        then(result.getEmail()).isEqualTo(patientDTO.getEmail());
        verify(patientRepositoryStub, times(1)).save(any(Patient.class));
    }
}