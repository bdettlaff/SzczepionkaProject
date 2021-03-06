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

    public Patient findPatientByUUID(UUID appointmentUUID) {
        Optional<Patient> patient = patientRepository.findByUuid(appointmentUUID);
        return patient.isPresent() ? patient.get() : null;
    }

    //TODO to use while creating email message to patient - can be moved to dedicated class
    public URL generatePatientLink(UUID patientUUID) {
        return PatientLinkGenerator.generateIndividualPatientUrl(patientUUID);
    }

    public Patient findPatientByPesel(String pesel) {
        Optional<Patient> patient = patientRepository.findByPesel(pesel);
        return patient.isPresent() ? patient.get() : null;
    }

    public Patient updatePatient(String pesel, PatientDTO patientDTO) {
        Patient patient = findPatientByPesel(pesel);
        patient.setEmail(patientDTO.getEmail());
        patient.setReferralId(patientDTO.getReferralId());
        patient.setIdNumber(patientDTO.getIdNumber());
        patient.setPostalCode(patientDTO.getPostalCode());

        return patientRepository.save(patient);
    }
}
