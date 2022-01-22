package com.barbershop.servicebarber_feedback.service.impl;

import com.barbershop.servicebarber_feedback.repository.BarberFeedbackRepository;
import com.barbershop.servicebarber_feedback.repository.model.BarberFeedback;
import com.barbershop.servicebarber_feedback.repository.model.User;
import com.barbershop.servicebarber_feedback.service.BarberFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class BarberFeedbackServiceImpl implements BarberFeedbackService {
    private final BarberFeedbackRepository barberFeedbackRepository;
    private final RestTemplate restTemplate;

    public List<BarberFeedback> fetchAllFeedbacks() {
        return barberFeedbackRepository.findAll();
    }

    public BarberFeedback fetchFeedbackById(long id) throws IllegalArgumentException {
        final Optional<BarberFeedback> maybeBarberService = barberFeedbackRepository.findById(id);

        if (maybeBarberService.isPresent())
            return maybeBarberService.get();
        else
            throw new IllegalArgumentException();
    }

    public List<BarberFeedback> fetchFeedbacksByClient(long id) throws IllegalArgumentException {
        return barberFeedbackRepository.findByClientId(id);
    }

    public List<BarberFeedback> fetchFeedbacksByBarber(long id) throws IllegalArgumentException {
        return barberFeedbackRepository.findByBarberId(id);
    }

    public String getClientName(long id) {
        BarberFeedback BarberFeedback = fetchFeedbackById(id);
        Long clientId = BarberFeedback.getClientId();

        User client = restTemplate.getForObject(
                "http://service-users:8080/users/" + clientId,
                User.class
        );
        return client.getFirstName();
    }

    public String getBarberName(long id) {
        BarberFeedback BarberFeedback = fetchFeedbackById(id);
        Long barberId = BarberFeedback.getClientId();

        User client = restTemplate.getForObject(
                "http://service-users:8080/users/" + barberId,
                User.class
        );
        return client.getFirstName();
    }

    public Double getAverageRatingForBarber(long id) throws IllegalArgumentException {
        List<BarberFeedback> ratingList = barberFeedbackRepository.findByBarberId(id);

        return ratingList.stream()
                .mapToDouble(BarberFeedback::getRating)
                .average()
                .orElse(Double.NaN);

        //return barberFeedbackRepository.getAverageFeedbackRatingByBarberId(id);
    }

    public long createBarberFeedback(
            Long clientId,
            Long barberId,
            Integer rating,
            String content
    ) {
        if (rating > 5){
            rating = 5;
        } else  if (rating < 0){
            rating = 1;
        }

        final BarberFeedback barberFeedback = BarberFeedback.builder()
                .clientId(clientId)
                .barberId(barberId)
                .rating(rating)
                .content(content)
                .datetime(LocalDateTime.now())
                .build();

        final BarberFeedback savedBarberFeedback = barberFeedbackRepository.save(barberFeedback);
        return savedBarberFeedback.getId();
    }

    public void deleteBarberFeedback(long id) {
        if(barberFeedbackRepository.existsById(id)){
            barberFeedbackRepository.deleteById(id);
        }
    }
}
