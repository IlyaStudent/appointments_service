package com.example.appointments.feature.appointment;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public enum AppointmentStatus {
    CREATED,
    CONFIRMED,
    WAITING,
    IN_PROGRESS,
    CANCELLED,
    COMPLETED;

    @JsonCreator
    public static AppointmentStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return AppointmentStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + value);
        }
    }

    public List<AppointmentStatus> canBeChangedFrom() {
        return switch (this) {
            case CREATED -> List.of();
            case CONFIRMED -> List.of(CREATED);
            case WAITING -> List.of(CONFIRMED);
            case IN_PROGRESS -> List.of(WAITING);
            case CANCELLED -> List.of(CREATED, CONFIRMED, WAITING, IN_PROGRESS);
            case COMPLETED -> List.of(IN_PROGRESS);
        };
    }
}
