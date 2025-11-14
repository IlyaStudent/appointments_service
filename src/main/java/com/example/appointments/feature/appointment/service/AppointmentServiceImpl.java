package com.example.appointments.feature.appointment.service;

import com.example.appointments.feature.appointment.Appointment;
import com.example.appointments.feature.appointment.AppointmentStatus;
import com.example.appointments.feature.appointment.dto.AppointmentResponse;
import com.example.appointments.feature.appointment.dto.CreateAppointmentRequest;
import com.example.appointments.feature.appointment.exceptions.AppointmentNotFoundException;
import com.example.appointments.feature.appointment.exceptions.InvalidStateTransitionException;
import com.example.appointments.feature.appointment.mapper.AppointmentMapper;
import com.example.appointments.feature.appointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            AppointmentMapper appointmentMapper
    ) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointmentMapper.toResponseList(appointments);
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        Appointment appointment = appointmentMapper.toEntity(request);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentResponse changeAppointmentStatus(Long id, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        AppointmentStatus currentStatus = appointment.getStatus();
        boolean isValidTransition = newStatus.canBeChangedFrom().contains(currentStatus);

        if (!isValidTransition) {
            throw new InvalidStateTransitionException(id, currentStatus, newStatus);
        }

        appointment.setStatus(newStatus);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(updatedAppointment);
    }
}
