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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/{locationId}")
    public Appointment newFirstAppointment(@RequestBody PatientDTO patientDTO, @PathVariable Long locationId) throws MessagingException {
        return appointmentService.newFirstAppointment(patientDTO, locationId);
    }

    @PostMapping("/2/{appointmentId}")
    public Appointment newSecondAppointment(@PathVariable Long appointmentId) {
        return appointmentService.newSecondAppointment(appointmentId);
    }

    @PatchMapping("/{appointmentNumber}/{appointmentId}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable int appointmentNumber,
                                                         @PathVariable Long appointmentId) {
        Appointment cancelAppointment = appointmentService.cancelAppointment(appointmentNumber, appointmentId);
        if (cancelAppointment != null) {
            return ResponseEntity.ok().body(cancelAppointment);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{patientUUID}")
    public ResponseEntity<AppointmentDetailsDTO> getAppointmentDetails(@PathVariable UUID patientUUID) {
        AppointmentDetailsDTO appointmentDetails = appointmentService.getAppointmentDetails(patientUUID);
        if (appointmentDetails != null) {
            return ResponseEntity.ok().body(appointmentDetails);
        }
        return ResponseEntity.notFound().build();
    }

}
