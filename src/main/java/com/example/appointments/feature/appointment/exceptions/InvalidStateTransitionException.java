package com.example.appointments.feature.appointment.exceptions;

import com.example.appointments.feature.appointment.AppointmentStatus;

public class InvalidStateTransitionException extends RuntimeException {

    private final Long appointmentId;
    private final AppointmentStatus currentStatus;
    private final AppointmentStatus requestedStatus;

    public InvalidStateTransitionException(
            Long appointmentId,
            AppointmentStatus currentStatus,
            AppointmentStatus requestedStatus
    ) {
        super(String.format(
                "Cannot transition appointment %d from %s to %s",
                appointmentId,
                currentStatus,
                requestedStatus
        ));
        this.appointmentId = appointmentId;
        this.currentStatus = currentStatus;
        this.requestedStatus = requestedStatus;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public AppointmentStatus getCurrentStatus() {
        return currentStatus;
    }

    public AppointmentStatus getRequestedStatus() {
        return requestedStatus;
    }
}
