package com.barbershop.servicebarber_feedback.repository;

import com.barbershop.servicebarber_feedback.repository.model.BarberFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BarberFeedbackRepository extends JpaRepository<BarberFeedback, Long> {
    List<BarberFeedback> findByClientId(long id);
    List<BarberFeedback> findByBarberId(long id);

    @Query("SELECT AVG(f.rating) FROM BarberFeedback f WHERE f.barberId = 1")
    Double getAverageFeedbackRatingByBarberId(long id);
}
