package com.example.appointments.feature.appointment.exceptions;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(Long id) {
        super("Could not find appointment-" + id);
    }
}
