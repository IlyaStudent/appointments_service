package com.example.appointments.feature.appointment.service;

import com.example.appointments.feature.appointment.AppointmentStatus;
import com.example.appointments.feature.appointment.dto.AppointmentResponse;
import com.example.appointments.feature.appointment.dto.CreateAppointmentRequest;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse createAppointment(CreateAppointmentRequest request);

    AppointmentResponse changeAppointmentStatus(Long id, AppointmentStatus newStatus);
}
