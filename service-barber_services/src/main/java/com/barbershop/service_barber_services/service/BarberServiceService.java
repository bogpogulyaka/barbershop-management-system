package com.barbershop.service_barber_services.service;

import com.barbershop.service_barber_services.repository.model.BarberService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BarberServiceService {
    List<BarberService> getAllBarberServices();
    BarberService getBarberServiceById(long id) throws IllegalArgumentException;
    long createBarberService(
            String name,
            String description,
            Integer duration,
            BigDecimal price
    );
    String updateBarberService(
            long id,
            String name,
            String description,
            Integer duration,
            BigDecimal price
    ) throws IllegalArgumentException;
    String deleteBarberService(long id) throws IllegalArgumentException;
}

