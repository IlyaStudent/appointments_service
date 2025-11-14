package com.example.appointments.feature.appointment;

public class AppointmentEndpoints {
    private AppointmentEndpoints() {
        throw new AssertionError();
    }

    public static final String BASE = "/api/appointments";
    public static final String BY_ID = "/{id}";
    public static final String CONFIRM = BY_ID + "/confirm";
    public static final String START = BY_ID + "/start";
    public static final String WAIT = BY_ID + "/wait";
    public static final String CANCEL = BY_ID + "/cancel";
    public static final String COMPLETE = BY_ID + "/complete";
}
