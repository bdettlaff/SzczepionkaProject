package com.szczepionka.controller;

import com.szczepionka.entity.Appointment;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.service.AppointmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointment")
    Appointment newAppointment(@RequestBody PatientDTO patientDTO) {
        return appointmentService.newAppointment(patientDTO);
    }
}
