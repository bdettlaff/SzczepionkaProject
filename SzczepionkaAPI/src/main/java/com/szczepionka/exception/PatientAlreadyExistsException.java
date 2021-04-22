package com.szczepionka.exception;

public class PatientAlreadyExistsException extends Throwable {
    public PatientAlreadyExistsException(String s) {
        super(s);
    }
}
