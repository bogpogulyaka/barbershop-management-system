package com.barbershop.serviceappointments.service.impl;

import com.barbershop.serviceappointments.repository.AppointmentRepository;
import com.barbershop.serviceappointments.repository.model.Appointment;
import com.barbershop.serviceappointments.repository.model.BarberService;
import com.barbershop.serviceappointments.repository.model.User;
import com.barbershop.serviceappointments.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    public List<Appointment> fetchAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment fetchAppointmentById(long id) throws IllegalArgumentException {
        final Optional<Appointment> maybeAppointment = appointmentRepository.findById(id);

        if (maybeAppointment.isPresent())
            return maybeAppointment.get();
        else
            throw new IllegalArgumentException();
    }

    public List<Appointment> fetchAppointmentsByClient(long id) throws IllegalArgumentException {
        return appointmentRepository.findByClientId(id);
    }

    public List<Appointment> fetchAppointmentsByBarber(long id) throws IllegalArgumentException {
        return appointmentRepository.findByBarberId(id);
    }

    public String getClientName(long id) {
        Appointment appointment = fetchAppointmentById(id);
        Long clientId = appointment.getClientId();

        User client = restTemplate.getForObject(
                "http://service-users:8080/users/" + clientId,
                User.class
        );
        return client.getFirstName();
    }

    public String getServiceName(long id) {
        Appointment appointment = fetchAppointmentById(id);
        Long serviceId = appointment.getServiceId();

        BarberService barberService = restTemplate.getForObject(
                "http://service-barber-services:8081/barber-services/" + serviceId,
                BarberService.class
        );
        return barberService.getName();
    }

    public long createAppointment(
            Long clientId,
            Long barberId,
            Long serviceId,
            LocalDateTime datetime,
            Integer duration,
            BigDecimal totalPrice
    ) {
        final Appointment appointment = Appointment.builder()
                .clientId(clientId)
                .barberId(barberId)
                .serviceId(serviceId)
                .datetime(datetime)
                .duration(duration)
                .totalPrice(totalPrice)
                .build();

        final Appointment savedAppointment = appointmentRepository.save(appointment);
        return savedAppointment.getId();
    }

    public void updateAppointment(long id, LocalDateTime datetime) throws IllegalArgumentException {
        final Optional<Appointment> maybeAppointment = appointmentRepository.findById(id);

        if (maybeAppointment.isEmpty())
            throw new IllegalArgumentException("Appointment does not exist");

        final Appointment appointment = maybeAppointment.get();

        if(datetime != null) appointment.setDatetime(datetime);

        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(long id) {
        if(appointmentRepository.existsById(id)){
            appointmentRepository.deleteById(id);
        }
    }
}
