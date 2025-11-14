package com.example.appointments.feature.appointment.controller;

import com.example.appointments.feature.appointment.AppointmentEndpoints;
import com.example.appointments.feature.appointment.AppointmentStatus;
import com.example.appointments.feature.appointment.dto.AppointmentResponse;
import com.example.appointments.feature.appointment.dto.CreateAppointmentRequest;
import com.example.appointments.feature.appointment.exceptions.InvalidStateTransitionException;
import com.example.appointments.feature.appointment.service.AppointmentService;
import com.example.appointments.feature.appointment.utils.AppointmentModelAssembler;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(AppointmentEndpoints.BASE)
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentModelAssembler assembler;

    public AppointmentController(
            AppointmentService appointmentService,
            AppointmentModelAssembler assembler
    ) {
        this.appointmentService = appointmentService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> responses = appointmentService.getAllAppointments();
        return assembler.toCollectionModel(responses);
    }

    @GetMapping(AppointmentEndpoints.BY_ID)
    public EntityModel<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        AppointmentResponse response = appointmentService.getAppointmentById(id);
        return assembler.toModel(response);
    }

    @PostMapping
    public ResponseEntity<EntityModel<AppointmentResponse>> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);

        return ResponseEntity
                .created(linkTo(methodOn(AppointmentController.class).getAppointmentById(response.id())).toUri())
                .body(assembler.toModel(response));
    }

    @PutMapping(AppointmentEndpoints.CONFIRM)
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id) {
       return changeAppointmentStatus(id, AppointmentStatus.CONFIRMED);
    }

    @PutMapping(AppointmentEndpoints.WAIT)
    public ResponseEntity<?> waitAppointment(@PathVariable Long id) {
        return changeAppointmentStatus(id, AppointmentStatus.WAITING);
    }

    @PutMapping(AppointmentEndpoints.START)
    public ResponseEntity<?> startAppointment(@PathVariable Long id) {
        return changeAppointmentStatus(id, AppointmentStatus.IN_PROGRESS);
    }

    @PutMapping(AppointmentEndpoints.CANCEL)
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        return changeAppointmentStatus(id, AppointmentStatus.CANCELLED);
    }


    @PutMapping(AppointmentEndpoints.COMPLETE)
    public ResponseEntity<?> completeAppointment(@PathVariable Long id) {
        return changeAppointmentStatus(id, AppointmentStatus.COMPLETED);
    }

    private ResponseEntity<?> changeAppointmentStatus(
            Long id,
            AppointmentStatus newStatus
    ) {
        try {
            AppointmentResponse response = appointmentService.changeAppointmentStatus(id, newStatus);
            EntityModel<AppointmentResponse> appointmentModel = assembler.toModel(response);
            return ResponseEntity.ok(appointmentModel);
        } catch (InvalidStateTransitionException e) {
            return buildInvalidStateTransitionResponse(e.getMessage());
        }
    }

    private ResponseEntity<?> buildInvalidStateTransitionResponse(String errorDetail) {
        Problem problem = Problem.create()
                .withTitle("Invalid state transition")
                .withDetail(errorDetail);
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(problem);
    }

}
