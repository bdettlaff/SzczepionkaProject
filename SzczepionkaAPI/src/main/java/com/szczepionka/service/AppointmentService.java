package com.szczepionka.service;

import com.szczepionka.entity.Appointment;
import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
    }

    public Appointment newAppointment(PatientDTO patientDTO) {
        Patient patient = patientService.addPatient(patientDTO);
        Appointment appointment = new Appointment();
        appointment.setId(patient.getId());
        return appointmentRepository.save(appointment);
    }
}
