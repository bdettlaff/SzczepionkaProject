package com.szczepionka.util;

import com.google.gson.Gson;
import com.szczepionka.model.VaccinationLocation;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class VaccinationLocationsFetcher {

    private Map<Long, VaccinationLocation> vaccinationLocations;

    public VaccinationLocationsFetcher() {
        fetchInitialData();
    }

    @SuppressWarnings("unchecked")
    private Map<Long, VaccinationLocation> fetchInitialData() {
        try (FileReader reader = new FileReader("src/main/resources/vaccination_locations.json")) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            Gson gson = new Gson();

            this.vaccinationLocations = (Map<Long, VaccinationLocation>) jsonArray.stream()
                    .map(object -> gson.fromJson(object.toString(), VaccinationLocation.class))
                    .collect(Collectors.toMap(VaccinationLocation::getId, location -> location));

            return vaccinationLocations;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Initial data file has not been found.");
    }

    public VaccinationLocation getById(long id) {
        return vaccinationLocations.get(id);
    }

    public Map<Long, VaccinationLocation> getAll() {
        return vaccinationLocations;
    }
}
