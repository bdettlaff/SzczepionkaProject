package com.szczepionka.exception;

public class PatientCantEnrollToSecondAppointment extends Throwable {
    public PatientCantEnrollToSecondAppointment(String s) {
        super(s);
    }
}
