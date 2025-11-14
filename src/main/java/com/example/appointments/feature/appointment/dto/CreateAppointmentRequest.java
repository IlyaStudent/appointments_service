package com.example.appointments.feature.appointment.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;


public record CreateAppointmentRequest(
        @NotBlank(message = "Service name is required")
        @Size(min = 1, max = 255, message = "Service name must be between 1 and 255 characters")
        String serviceName,

        @NotBlank(message = "Client name is required")
        @Size(min = 1, max = 255, message = "Client name must be between 1 and 255 characters")
        String clientName,

        @NotNull(message = "Appointment date/time is required")
        LocalDateTime dateTime,

        @NotNull(message = "Duration is required")
        Duration duration,

        @NotNull(message = "Price is required")
        @Digits(integer = 10, fraction = 2, message = "Price must have at most 10 digits and 2 decimal places")
        BigDecimal price
) {
}
