package com.barbershop.service_barber_services.service.impl;

import com.barbershop.service_barber_services.repository.BarberServiceRepository;
import com.barbershop.service_barber_services.repository.model.BarberService;
import com.barbershop.service_barber_services.service.BarberServiceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class BarberServicesServiceImpl implements BarberServiceService {
    private final BarberServiceRepository barberServiceRepository;

    public List<BarberService> getAllBarberServices() {
        return barberServiceRepository.findAll();
    }

    public BarberService getBarberServiceById(long id) throws IllegalArgumentException {
        final Optional<BarberService> maybeBarberService = barberServiceRepository.findById(id);

        if (maybeBarberService.isPresent())
            return maybeBarberService.get();
        else
            throw new IllegalArgumentException();
    }

    public long createBarberService(
            String name,
            String description,
            Integer duration,
            BigDecimal price
    ) {
        final BarberService barberService = BarberService.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .price(price)
                .build();

        final BarberService savedBarberService = barberServiceRepository.save(barberService);
        return savedBarberService.getId();
    }

    public String updateBarberService(
            long id,
            String name,
            String description,
            Integer duration,
            BigDecimal price
    ) throws IllegalArgumentException {
        final Optional<BarberService> maybeBarberService = barberServiceRepository.findById(id);

        if (maybeBarberService.isPresent()) {
            final BarberService barberService = maybeBarberService.get();

            if(name != null && !name.isBlank()) barberService.setName(name);
            if(description != null && !description.isBlank()) barberService.setName(description);
            if(duration != null && duration > 0) barberService.setDuration(duration);
            if(price != null && price.signum() == 1) barberService.setPrice(price);
            try {
                barberServiceRepository.save(maybeBarberService.get());
                return "Barber service with id="+String.valueOf(id)+" was successfully updated";
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("");
            }
        }
        throw new IllegalArgumentException("Barber service not found");
    }

    public String deleteBarberService(long id) throws IllegalArgumentException {
        if(barberServiceRepository.existsById(id)){
            barberServiceRepository.deleteById(id);
            return "Barber service with id="+String.valueOf(id)+" was successfully deleted";
        }
        throw new IllegalIdentifierException("Barber service does not exist");
    }
}
