package com.szczepionka.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class PatientLinkGenerator {

    public static URL generateIndividualPatientUrl(UUID patientUuid) {
        try {
            return new URL("http://localhost:8080/appointment/" + patientUuid);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
