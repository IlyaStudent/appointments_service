package com.example.appointments.feature.appointment.exceptions;

import com.example.appointments.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AppointmentNotFoundException extends BaseException {

    public AppointmentNotFoundException(Long id) {
        super("Could not find appointment-" + id, HttpStatus.NOT_FOUND, "APPOINTMENT_NOT_FOUND");
    }
}
