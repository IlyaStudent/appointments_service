package com.example.appointments.feature.appointment.repository;

import com.example.appointments.feature.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
