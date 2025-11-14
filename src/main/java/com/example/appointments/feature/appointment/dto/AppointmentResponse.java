package com.example.appointments.feature.appointment.dto;

import com.example.appointments.feature.appointment.AppointmentStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        String serviceName,
        String clientName,
        LocalDateTime dateTime,
        Duration duration,
        BigDecimal price,
        AppointmentStatus status
) {
}
