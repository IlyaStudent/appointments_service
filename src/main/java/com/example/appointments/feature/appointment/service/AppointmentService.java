package com.example.appointments.feature.appointment.service;

import com.example.appointments.feature.appointment.AppointmentStatus;
import com.example.appointments.feature.appointment.dto.AppointmentResponse;
import com.example.appointments.feature.appointment.dto.CreateAppointmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {

    Page<AppointmentResponse> getAllAppointments(Pageable pageable);

    Page<AppointmentResponse> searchAppointments(String query, Pageable pageable);

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse createAppointment(CreateAppointmentRequest request);

    AppointmentResponse changeAppointmentStatus(Long id, AppointmentStatus newStatus);
}
