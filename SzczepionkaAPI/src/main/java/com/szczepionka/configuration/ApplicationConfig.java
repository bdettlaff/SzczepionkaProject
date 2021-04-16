package com.szczepionka.configuration;

import com.szczepionka.util.DistanceCalculator;
import com.szczepionka.util.VaccinationLocationsFetcher;
import com.szczepionka.util.ZipcodeConverter;
import com.szczepionka.util.PatientLinkGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

    @Bean
    public VaccinationLocationsFetcher vaccinationLocations() {
        return new VaccinationLocationsFetcher();
    }

    @Bean
    public DistanceCalculator distanceCalculator() {
        return new DistanceCalculator();
    }

    @Bean
    public ZipcodeConverter zipcodeConverter() {
        return new ZipcodeConverter();
    }

    public PatientLinkGenerator patientLinkGenerator() {
        return new PatientLinkGenerator();
    }
}
