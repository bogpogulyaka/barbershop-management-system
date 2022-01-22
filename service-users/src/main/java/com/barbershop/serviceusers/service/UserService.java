package com.barbershop.serviceusers.service;

import com.barbershop.serviceusers.repository.model.User;
import com.barbershop.serviceusers.repository.model.UserRole;

import java.util.List;

public interface UserService {
    List<User> fetchAllUsers();
    User fetchUserById(long id) throws IllegalArgumentException;
    List<User> fetchUsersByRole(UserRole role) throws IllegalArgumentException;
    long createUser(
            String email,
            String password,
            String firstName,
            String lastName,
            UserRole role,
            String phoneNumber);
    void updateUser(
            long id,
            String firstName,
            String lastName,
            UserRole role,
            String phoneNumber
            ) throws IllegalArgumentException;
    void deleteUser(long id);
}

