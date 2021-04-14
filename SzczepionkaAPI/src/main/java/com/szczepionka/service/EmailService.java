package com.szczepionka.service;


import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.PatientRepository;
import com.szczepionka.util.PatientLinkGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import java.net.URL;
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


    public PatientDTO sendMail(Long patientId) throws MessagingException {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            Patient patientUUID = Patient.builder().
                    uuid(patient.get().getUuid()).build();
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

            URL url = PatientLinkGenerator.generateIndividualPatientUrl(patientUUID.getUuid());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(patientDTO.getEmail()));
            msg.setSubject("Potwierdzenie zapisu na szczepienie");
            msg.setSentDate(new Date());
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("Zostałeś/aś zarejestrowany/a na szczepienie"
                            + "\nDane:\n"
                            + "Pesel:\t" + patientDTO.getPesel().replace(patientDTO.getPesel().substring(3, 7), "****")
                            + "\nKod pocztowy:\t" + patientDTO.getPostalCode()
                            + "\nAby zobaczyć szczegóły szczepienia, kliknij w link poniżej\t" + url,
                    "text/plain; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);

            return patientDTO;
        }
        return null;
    }
}

