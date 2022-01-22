package com.barbershop.serviceusers.api.dto;

import com.barbershop.serviceusers.repository.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String phoneNumber;
}
