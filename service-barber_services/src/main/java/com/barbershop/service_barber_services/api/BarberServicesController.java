package com.barbershop.service_barber_services.api;

import com.barbershop.service_barber_services.repository.model.BarberService;
import com.barbershop.service_barber_services.service.impl.BarberServicesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/barber-services")
@RestController
public final class BarberServicesController {
    private final BarberServicesServiceImpl barberServicesServiceImpl;

    @GetMapping
    public ResponseEntity<List<BarberService>> index() {
        final List<BarberService> barberServices = barberServicesServiceImpl.getAllBarberServices();
        return ResponseEntity.ok(barberServices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberService> getById(@PathVariable long id) {
        try {
            final BarberService barberService = barberServicesServiceImpl.getBarberServiceById(id);
            return ResponseEntity.ok(barberService);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.barbershop.service_barber_services.api.dto.BarberService barberService) {
        final String name = barberService.getName();
        final String description = barberService.getDescription();
        final Integer duration = barberService.getDuration();
        final BigDecimal price = barberService.getPrice();

        try {
            final long id = barberServicesServiceImpl.createBarberService(name, description, duration, price);
            final String userUri = String.format("/users/%s", id);

            return ResponseEntity.created(URI.create(userUri)).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody com.barbershop.service_barber_services.api.dto.BarberService barberService) {
        final String name = barberService.getName();
        final String description = barberService.getDescription();
        final Integer duration = barberService.getDuration();
        final BigDecimal price = barberService.getPrice();

        try {
            barberServicesServiceImpl.updateBarberService(id, name, description, duration, price);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        barberServicesServiceImpl.deleteBarberService(id);
        return ResponseEntity.noContent().build();
    }
}
