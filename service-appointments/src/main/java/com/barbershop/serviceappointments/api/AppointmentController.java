package com.barbershop.serviceappointments.api;

import com.barbershop.serviceappointments.repository.model.Appointment;
import com.barbershop.serviceappointments.service.impl.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/appointments")
@RestController
public final class AppointmentController {
    private final AppointmentServiceImpl appointmentServiceImpl;

    @GetMapping
    public ResponseEntity<List<Appointment>> index() {
        final List<Appointment> appointments = appointmentServiceImpl.fetchAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable long id) {
        try {
            final Appointment appointment = appointmentServiceImpl.fetchAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-client/{id}")
    public ResponseEntity<List<Appointment>> getByClientId(@PathVariable long id) {
        final List<Appointment> appointments = appointmentServiceImpl.fetchAppointmentsByClient(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/by-barber/{id}")
    public ResponseEntity<List<Appointment>> getByBarberId(@PathVariable long id) {
        final List<Appointment> appointments = appointmentServiceImpl.fetchAppointmentsByBarber(id);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/client-name/{id}")
    public ResponseEntity<String> getClientName(@PathVariable long id) {
        try {
            final String clientName = appointmentServiceImpl.getClientName(id);
            return ResponseEntity.ok(clientName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/service-name/{id}")
    public ResponseEntity<String> getServiceName(@PathVariable long id) {
        try {
            final String serviceName = appointmentServiceImpl.getServiceName(id);
            return ResponseEntity.ok(serviceName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.barbershop.serviceappointments.api.dto.Appointment appointment) {
        final Long clientId = appointment.getClientId();
        final Long barberId = appointment.getBarberId();
        final Long serviceId = appointment.getServiceId();
        final LocalDateTime datetime = appointment.getDatetime();
        final Integer duration = 0;
        final BigDecimal totalPrice = new BigDecimal("0");

        try {
            final long id = appointmentServiceImpl.createAppointment(clientId, barberId, serviceId, datetime, duration, totalPrice);
            final String userUri = String.format("/appointments/%s", id);

            return ResponseEntity.created(URI.create(userUri)).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody com.barbershop.serviceappointments.api.dto.Appointment appointment) {
        final LocalDateTime datetime = appointment.getDatetime();

        try {
            appointmentServiceImpl.updateAppointment(id, datetime);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        appointmentServiceImpl.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
