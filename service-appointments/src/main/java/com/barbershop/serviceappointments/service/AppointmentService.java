package com.barbershop.serviceappointments.service;

import com.barbershop.serviceappointments.repository.model.Appointment;
import com.barbershop.serviceappointments.repository.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<Appointment> fetchAllAppointments();
    Appointment fetchAppointmentById(long id) throws IllegalArgumentException;
    List<Appointment> fetchAppointmentsByClient(long id) throws IllegalArgumentException;
    List<Appointment> fetchAppointmentsByBarber(long id) throws IllegalArgumentException;
    String getClientName(long id);
    String getServiceName(long id);

    long createAppointment(
            Long clientId,
            Long barberId,
            Long serviceId,
            LocalDateTime datetime,
            Integer duration,
            BigDecimal totalPrice);
    void updateAppointment(
            long id,
            LocalDateTime datetime
            ) throws IllegalArgumentException;
    void deleteAppointment(long id);
}

