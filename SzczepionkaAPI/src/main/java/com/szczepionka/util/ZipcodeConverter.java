package com.szczepionka.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.szczepionka.model.Coordinates;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class ZipcodeConverter {

    private static final String zipcodebaseKey = "#####";

    public Coordinates zipcodeToCoordinates(String zipcode) {
        final String uri = "https://app.zipcodebase.com/api/v1/search?apikey=" + zipcodebaseKey + "&codes=" + zipcode;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        assert result != null;
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject().get("results").getAsJsonObject().get(zipcode).getAsJsonArray().get(0).getAsJsonObject();
        return new Coordinates(BigDecimal.valueOf(jsonObject.get("latitude").getAsDouble()), BigDecimal.valueOf(jsonObject.get("longitude").getAsDouble()));
    }
}
