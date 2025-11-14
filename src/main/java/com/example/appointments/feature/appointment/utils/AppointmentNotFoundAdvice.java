package com.example.appointments.feature.appointment.utils;

import com.example.appointments.feature.appointment.exceptions.AppointmentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppointmentNotFoundAdvice {

    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String appointmentNotFoundException(AppointmentNotFoundException e) {
        return e.getMessage();
    }
}
