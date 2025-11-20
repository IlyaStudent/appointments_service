package com.example.appointments.feature.appointment.repository;

import com.example.appointments.feature.appointment.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAll(Pageable pageable);

    Page<Appointment> findByServiceNameContainingIgnoreCase(String serviceName, Pageable pageable);

    Page<Appointment> findByClientNameContainingIgnoreCase(String clientName, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE " +
            "LOWER(a.serviceName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.clientName) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Appointment> searchByServiceNameOrClientName(@Param("query") String query, Pageable pageable);
}
