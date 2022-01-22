package com.barbershop.servicebarber_feedback.api;

import com.barbershop.servicebarber_feedback.repository.model.BarberFeedback;
import com.barbershop.servicebarber_feedback.service.impl.BarberFeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/barber-feedbacks")
@RestController
public final class BarberFeedbackController {
    private final BarberFeedbackServiceImpl barberFeedbackServiceImpl;

    @GetMapping
    public ResponseEntity<List<BarberFeedback>> index() {
        final List<BarberFeedback> barberFeedbacks = barberFeedbackServiceImpl.fetchAllFeedbacks();
        return ResponseEntity.ok(barberFeedbacks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberFeedback> getById(@PathVariable long id) {
        try {
            final BarberFeedback barberFeedback = barberFeedbackServiceImpl.fetchFeedbackById(id);
            return ResponseEntity.ok(barberFeedback);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-client/{id}")
    public ResponseEntity<List<BarberFeedback>> getByClientId(@PathVariable long id) {
        final List<BarberFeedback> barberFeedbacks = barberFeedbackServiceImpl.fetchFeedbacksByClient(id);
        return ResponseEntity.ok(barberFeedbacks);
    }

    @GetMapping("/by-barber/{id}")
    public ResponseEntity<List<BarberFeedback>> getByBarberId(@PathVariable long id) {
        final List<BarberFeedback> barberFeedbacks = barberFeedbackServiceImpl.fetchFeedbacksByBarber(id);
        return ResponseEntity.ok(barberFeedbacks);
    }

    @GetMapping("/client-name/{id}")
    public ResponseEntity<String> getClientName(@PathVariable long id) {
        try {
            final String clientName = barberFeedbackServiceImpl.getClientName(id);
            return ResponseEntity.ok(clientName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/barber-name/{id}")
    public ResponseEntity<String> getBarberName(@PathVariable long id) {
        try {
            final String clientName = barberFeedbackServiceImpl.getClientName(id);
            return ResponseEntity.ok(clientName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("barber-average-rating/{id}")
    public ResponseEntity<Double> getAverageByBarberId(@PathVariable long id) {
        final Double rating = barberFeedbackServiceImpl.getAverageRatingForBarber(id);
        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.barbershop.servicebarber_feedback.api.dto.BarberFeedback barberFeedback) {
        final Long clientId = barberFeedback.getClientId();
        final Long barberId = barberFeedback.getBarberId();
        final Integer rating = barberFeedback.getRating();
        final String content = barberFeedback.getContent();

        try {
            final long id = barberFeedbackServiceImpl.createBarberFeedback(clientId, barberId, rating, content);
            final String userUri = String.format("/barber-feedback/%s", id);

            return ResponseEntity.created(URI.create(userUri)).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        barberFeedbackServiceImpl.deleteBarberFeedback(id);
        return ResponseEntity.noContent().build();
    }
}
