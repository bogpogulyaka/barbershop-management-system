package com.barbershop.serviceappointments.repository;

import com.barbershop.serviceappointments.repository.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClientId(long id);
    List<Appointment> findByBarberId(long id);
}
