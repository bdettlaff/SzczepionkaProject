package com.szczepionka.service;


import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {

    private final PatientRepository patientRepository;

    @Value("${gmail.username}")
    private String username;

    @Value("${gmail.password}")
    private String password;

    public EmailService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public PatientDTO sendMail(Long patientId) throws AddressException, MessagingException, IOException {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            PatientDTO patientDTO = PatientDTO.builder().
                    email(patient.get().getEmail())
                    .pesel(patient.get().getPesel())
                    .postalCode(patient.get().getPostalCode())
                    .referralId(patient.get().getReferralId())
                    .build();

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username, false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(patientDTO.getEmail()));
            msg.setSubject("Potwierdzenie zapisu na szczepienie");
            msg.setSentDate(new Date());
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("Zostałeś/aś zarejestrowany/a na szczepienie"
                            + "\nDane:\n"
                            + "Pesel:\t" + patientDTO.getPesel().replace(patientDTO.getPesel().substring(3, 7), "****")
                            + "\nKod pocztowy:\t" + patientDTO.getPostalCode()
                            + "\nAby zobaczyć szczegóły szczepienia, kliknij w link poniżej\t" + constructUri(patientDTO.getReferralId()),
                    "text/plain; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);

            return patientDTO;
        }
        return null;
    }


    private String constructUri(String url) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("localhost:4200")
                .path("/" + url).query("q={keywords}").buildAndExpand("szczepienie");

        return uriComponents.toString();
    }
}

