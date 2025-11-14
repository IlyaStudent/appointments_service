package com.example.appointments.feature.appointment.utils;

import com.example.appointments.feature.appointment.AppointmentEndpoints;
import com.example.appointments.feature.appointment.AppointmentStatus;
import com.example.appointments.feature.appointment.controller.AppointmentController;
import com.example.appointments.feature.appointment.dto.AppointmentResponse;
import jakarta.annotation.Nonnull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppointmentModelAssembler implements RepresentationModelAssembler<AppointmentResponse, EntityModel<AppointmentResponse>> {

    private static final String CANCEL_REL = "cancel";
    private static final String CONFIRM_REL = "confirm";
    private static final String WAIT_REL = "wait";
    private static final String START_REL = "start";
    private static final String COMPLETE_REL = "complete";

    private static final Set<AppointmentStatus> CANCELLABLE_STATUSES = new HashSet<>(
            Arrays.asList(AppointmentStatus.CREATED, AppointmentStatus.CONFIRMED,
                    AppointmentStatus.WAITING, AppointmentStatus.IN_PROGRESS)
    );

    @Override
    public @Nonnull EntityModel<AppointmentResponse> toModel(@Nonnull AppointmentResponse response) {
        EntityModel<AppointmentResponse> appointmentEntityModel = EntityModel.of(
                response,
                createSelfLink(response.id()),
                createBaseLink()
        );

        addCommonLinks(appointmentEntityModel, response.id());
        addStatusSpecificLinks(appointmentEntityModel, response);

        return appointmentEntityModel;
    }

    @Override
    public @Nonnull CollectionModel<EntityModel<AppointmentResponse>> toCollectionModel(
            @Nonnull Iterable<? extends AppointmentResponse> entities
    ) {
        return RepresentationModelAssembler.super
                .toCollectionModel(entities)
                .add(createBaseLink());
    }

    private Link createSelfLink(Long appointmentId) {
        return linkTo(methodOn(AppointmentController.class).getAppointmentById(appointmentId)).withSelfRel();
    }

    private Link createBaseLink() {
        return linkTo(methodOn(AppointmentController.class).getAllAppointments()).withRel(AppointmentEndpoints.BASE);
    }

    private void addCommonLinks(EntityModel<AppointmentResponse> model, Long appointmentId) {
        if (model.getContent() != null && CANCELLABLE_STATUSES.contains(model.getContent().status())) {
            model.add(createStatusLink(appointmentId, CANCEL_REL,
                    id -> methodOn(AppointmentController.class).cancelAppointment(id)));
        }
    }

    private void addStatusSpecificLinks(EntityModel<AppointmentResponse> model, AppointmentResponse response) {
        Long appointmentId = response.id();
        AppointmentStatus status = response.status();

        switch (status) {
            case CREATED -> model.add(
                    createStatusLink(appointmentId, CONFIRM_REL,
                            id -> methodOn(AppointmentController.class).confirmAppointment(id))
            );
            case CONFIRMED -> model.add(
                    createStatusLink(appointmentId, WAIT_REL,
                            id -> methodOn(AppointmentController.class).waitAppointment(id))
            );
            case WAITING -> model.add(
                    createStatusLink(appointmentId, START_REL,
                            id -> methodOn(AppointmentController.class).startAppointment(id))
            );
            case IN_PROGRESS -> model.add(
                    createStatusLink(appointmentId, COMPLETE_REL,
                            id -> methodOn(AppointmentController.class).completeAppointment(id))
            );
            case CANCELLED, COMPLETED -> {
            }
        }
    }

    private <T> Link createStatusLink(Long appointmentId, String rel, Function<Long, T> methodSupplier) {
        return linkTo(methodSupplier.apply(appointmentId)).withRel(rel);
    }
}
