package com.barbershop.service_barber_services.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberService {
    private String name;
    private String description;
    private Integer duration;
    private BigDecimal price;
}
