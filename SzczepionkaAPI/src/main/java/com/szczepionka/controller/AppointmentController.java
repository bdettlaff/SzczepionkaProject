package com.szczepionka.controller;

import com.szczepionka.entity.Appointment;
import com.szczepionka.model.AppointmentDetailsDTO;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointment/{locationId}")
    public Appointment newAppointment(@RequestBody PatientDTO patientDTO, @PathVariable Long locationId) {
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

    @GetMapping("/appointment/{appointmentId}") //todo change appointmentId to radnom-generated uuid (to be send by email)
    public ResponseEntity<AppointmentDetailsDTO> getAppointmentDetails(@PathVariable Long appointmentId) {
        AppointmentDetailsDTO appointmentDetails = appointmentService.getAppointmentDetails(appointmentId);
        if(appointmentDetails != null) {
            return ResponseEntity.ok().body(appointmentDetails);
        }
        return ResponseEntity.notFound().build();
    }

}
