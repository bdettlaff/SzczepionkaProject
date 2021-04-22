package com.szczepionka.controller;

import com.szczepionka.entity.Appointment;
import com.szczepionka.exception.PatientAlreadyExistsException;
import com.szczepionka.exception.PatientCantEnrollToSecondAppointment;
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
    public ResponseEntity newFirstAppointment(@RequestBody PatientDTO patientDTO, @PathVariable Long locationId)
            throws MessagingException, PatientAlreadyExistsException {
        Appointment appointment = appointmentService.enrollFirstAppointment(patientDTO, locationId);
        return ResponseEntity.ok().body(appointment);
    }

    @PostMapping("/2/{appointmentId}")
    public ResponseEntity newSecondAppointment(@PathVariable Long appointmentId) throws PatientCantEnrollToSecondAppointment {
        Appointment appointment = appointmentService.enrollSecondAppointment(appointmentId);
        return ResponseEntity.ok().body(appointment);
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

    @PostMapping("/makeTimeFly/1/{appointmentId}")
    public ResponseEntity makeFirstAppointmentDone(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.makeFirstAppointmentDone(appointmentId);
        return ResponseEntity.ok().body(appointment);
    }
}
