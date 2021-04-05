package com.szczepionka.service;

import javax.mail.*;
import javax.mail.internet.*;

import com.szczepionka.entity.Patient;
import com.szczepionka.model.PatientDTO;
import com.szczepionka.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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


    public PatientDTO sendmail(Long patientId) throws  AddressException, MessagingException, IOException {
        Optional <Patient> patient = patientRepository.findById(patientId);
        if(patient.isPresent()) {
            PatientDTO patientDTO = PatientDTO.builder().
                    email(patient.get().getEmail())
                    .pesel(patient.get().getPesel())
                    .postalCode(patient.get().getPostalCode())
                    .referralId(patient.get().getReferralId())
                    .build();

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
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
            messageBodyPart.setContent("Zostałeś/aś zrejstrowana na szczepienie "
                    +"\nDane:\n"
                    +"Pesel:\t"+maskString(patientDTO.getPesel(),3, 7, '*')
                    +"\nKod pocztowy:\t" +patientDTO.getPostalCode()
                    +"\nAby zobaczyć szczegóły szczepienia, kliknij w link poniżej\t"+ constructUri(patientDTO.getReferralId()),
                    "text/plain; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);

            Transport.send(msg);

        return patientDTO;
        }
        return null;
    }

    private String maskString(String strText, int start, int end, char maskChar){
        if(strText == null || strText.equals(""))
            return "";

        if(start < 0)
            start = 0;

        if(end > strText.length() )
            end = strText.length();

        int maskLen = end - start;

        if(maskLen == 0)
            return strText;

        StringBuilder sbMaskString = new StringBuilder(maskLen);

        for(int i = 0; i < maskLen; i++){
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + maskLen);
    }

    private String constructUri(String url) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("localhost:4200")
                .path("/"+url).query("q={keywords}").buildAndExpand("szczepienie");

    return uriComponents.toString();
    }
}

