package com.barbershop.servicebarber_feedback.service;

import com.barbershop.servicebarber_feedback.repository.model.BarberFeedback;

import java.util.List;

public interface BarberFeedbackService {
    List<BarberFeedback> fetchAllFeedbacks();
    BarberFeedback fetchFeedbackById(long id) throws IllegalArgumentException;
    List<BarberFeedback> fetchFeedbacksByClient(long id) throws IllegalArgumentException;
    List<BarberFeedback> fetchFeedbacksByBarber(long id) throws IllegalArgumentException;
    Double getAverageRatingForBarber(long id) throws IllegalArgumentException;
    String getClientName(long id);
    String getBarberName(long id);

    long createBarberFeedback(
            Long clientId,
            Long barberId,
            Integer rating,
            String content);
    void deleteBarberFeedback(long id);
}

