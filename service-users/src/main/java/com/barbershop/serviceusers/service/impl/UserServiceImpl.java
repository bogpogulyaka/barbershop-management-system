package com.barbershop.serviceusers.service.impl;

import com.barbershop.serviceusers.repository.UserRepository;
import com.barbershop.serviceusers.repository.model.User;
import com.barbershop.serviceusers.repository.model.UserRole;
import com.barbershop.serviceusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public User fetchUserById(long id) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepository.findById(id);

        if (maybeUser.isPresent())
            return maybeUser.get();
        else
            throw new IllegalArgumentException();
    }

    public List<User> fetchUsersByRole(UserRole role) throws IllegalArgumentException {
        return userRepository.findByRole(role);
    }

    public long createUser(
            String email,
            String password,
            String firstName,
            String lastName,
            UserRole role,
            String phoneNumber
    ) {
        final User user = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .phoneNumber(phoneNumber)
                .build();

        final User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public void updateUser(
            long id,
            String firstName,
            String lastName,
            UserRole role,
            String phoneNumber
    ) throws IllegalArgumentException {
        final Optional<User> maybeUser = userRepository.findById(id);

        if (maybeUser.isEmpty())
            throw new IllegalArgumentException("User does not exist");

        final User user = maybeUser.get();

        if(firstName != null && !firstName.isBlank()) user.setFirstName(firstName);
        if(lastName != null && !lastName.isBlank()) user.setLastName(lastName);
        if(role != null) user.setRole(role);
        if(phoneNumber != null && !phoneNumber.isBlank()) user.setPhoneNumber(phoneNumber);

        userRepository.save(user);
    }

    public void deleteUser(long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }
}
