package com.example.appointments.feature.appointment.mapper;

import com.example.appointments.feature.appointment.Appointment;
import com.example.appointments.feature.appointment.dto.AppointmentResponse;
import com.example.appointments.feature.appointment.dto.CreateAppointmentRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return new AppointmentResponse(
            appointment.getId(),
            appointment.getServiceName(),
            appointment.getClientName(),
            appointment.getDateTime(),
            appointment.getDuration(),
            appointment.getPrice(),
            appointment.getStatus()
        );
    }

    public Appointment toEntity(CreateAppointmentRequest request) {
        Objects.requireNonNull(request, "CreateAppointmentRequest must not be null");

        return new Appointment(
            request.serviceName(),
            request.clientName(),
            request.dateTime(),
            request.duration(),
            request.price()
        );
    }

    public List<AppointmentResponse> toResponseList(List<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return List.of();
        }

        return appointments.stream()
            .map(this::toResponse)
            .toList();
    }

    public void updateEntityFromRequest(CreateAppointmentRequest request, Appointment appointment) {
        Objects.requireNonNull(request, "CreateAppointmentRequest must not be null");
        Objects.requireNonNull(appointment, "Appointment must not be null");

        appointment.setServiceName(request.serviceName());
        appointment.setClientName(request.clientName());
        appointment.setDateTime(request.dateTime());
        appointment.setDuration(request.duration());
        appointment.setPrice(request.price());
    }
}
