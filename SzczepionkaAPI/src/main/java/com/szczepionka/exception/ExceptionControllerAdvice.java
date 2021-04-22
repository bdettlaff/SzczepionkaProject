package com.szczepionka.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(PatientAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error cellNotEmpty(PatientAlreadyExistsException ex) {
        return new Error(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(PatientCantEnrollToSecondAppointment.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error cellNotEmpty(PatientCantEnrollToSecondAppointment ex) {
        return new Error(ex.getMessage());
    }

    @Data
    public static class Error {
        final String errorMessage;
    }
}
