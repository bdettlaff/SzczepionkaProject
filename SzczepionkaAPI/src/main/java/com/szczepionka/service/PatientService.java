package com.szczepionka.service;

import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.PatientRepository;
import com.szczepionka.util.PatientLinkGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public Patient addPatient(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        return patientRepository.save(patient);
    }

    public Patient findPatient(Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient.isPresent() ? patient.get() : null;
    }

    //TODO to use while creating email message to patient - can be moved to dedicated class
    public URL generatePatientLink(UUID patientUUID) {
        return PatientLinkGenerator.generateIndividualPatientUrl(patientUUID);
    }

}
