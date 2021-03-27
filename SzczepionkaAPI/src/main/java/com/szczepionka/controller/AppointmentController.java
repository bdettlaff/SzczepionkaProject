package com.szczepionka.controller;

import com.szczepionka.entity.Appointment;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointment/{locationId}")
    Appointment newAppointment(@RequestBody PatientDTO patientDTO, @PathVariable Long locationId) {
        return appointmentService.newAppointment(patientDTO, locationId);
    }

    @PatchMapping("/appointment/{appointmentId}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long appointmentId) {
        Appointment cancelAppointment = appointmentService.cancelAppointment(appointmentId);
        if(cancelAppointment != null) {
            return ResponseEntity.ok().body(cancelAppointment);
        }
        return ResponseEntity.notFound().build();
    }

}
