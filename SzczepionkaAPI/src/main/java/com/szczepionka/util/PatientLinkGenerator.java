package com.szczepionka.util;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Component
public class PatientLinkGenerator {

    private final URL url;

    //Solution if we would not use LOCALHOST:
//
//    public PatientLinkGenerator(Environment env) throws UnknownHostException, MalformedURLException {
//        url = new URL("http://" + InetAddress.getLocalHost().getHostName() + ":" + "8080");
//    }

    public PatientLinkGenerator() throws MalformedURLException {
        url = new URL("http://localhost:8080");
    }

    public URL generateIndividualPatientUrl(UUID patientUuid) {
        String path ="/appointment/" + patientUuid ;
        try {
            return new URL(url, path);
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }
    }
}
