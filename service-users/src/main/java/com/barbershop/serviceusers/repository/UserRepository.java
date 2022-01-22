package com.barbershop.serviceusers.repository;

import com.barbershop.serviceusers.repository.model.User;
import com.barbershop.serviceusers.repository.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(UserRole role);
}
