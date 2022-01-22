package com.barbershop.servicebarber_feedback.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberFeedback {
    private Long clientId;
    private Long barberId;
    private Integer rating;
    private String content;
}
