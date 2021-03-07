package com.szczepionka.util;

import com.szczepionka.model.VaccinationLocation;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class VaccinationLocationsFetcher {

    @SuppressWarnings("unchecked")
    public List<VaccinationLocation> fetchInitialData() {
        try (FileReader reader = new FileReader("src/main/resources/vaccination_locations.json")) {
            JSONParser jsonParser = new JSONParser();
            return  (List<VaccinationLocation>) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Initial data file has not been found.");
    }
}
