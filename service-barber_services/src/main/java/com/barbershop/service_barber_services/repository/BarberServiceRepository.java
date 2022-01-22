package com.barbershop.service_barber_services.repository;

import com.barbershop.service_barber_services.repository.model.BarberService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberServiceRepository extends JpaRepository<BarberService, Long> {
}
