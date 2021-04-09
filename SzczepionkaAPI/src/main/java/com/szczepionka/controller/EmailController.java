package com.szczepionka.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.szczepionka.model.PatientDTO;
import com.szczepionka.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class EmailController {

    public final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @RequestMapping(value = "/send/{patientId}", method = RequestMethod.POST)
    public ResponseEntity<String> sendEmail(@PathVariable Long patientId) throws AddressException, MessagingException, IOException {
        PatientDTO patientDTO = emailService.sendMail(patientId);
        if (patientDTO != null) {
            return ResponseEntity.ok("Email sent");
        }
        return ResponseEntity.ok("There is no such a patient");
    }

}
