package com.barbershop.serviceusers.api;

import com.barbershop.serviceusers.repository.model.User;
import com.barbershop.serviceusers.repository.model.UserRole;
import com.barbershop.serviceusers.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public final class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<User>> index() {
        final List<User> users = userServiceImpl.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        try {
            final User user = userServiceImpl.fetchUserById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{role}s")
    public ResponseEntity<List<User>> getByRole(@PathVariable UserRole role) {
        final List<User> users = userServiceImpl.fetchUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.barbershop.serviceusers.api.dto.User user) {
        final String email = user.getEmail();
        final String password = user.getPassword();
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final UserRole role = user.getRole();
        final String phoneNumber = user.getPhoneNumber();

        try {
            final long id = userServiceImpl.createUser(email, password, firstName, lastName, role, phoneNumber);
            final String userUri = String.format("/users/%s", id);

            return ResponseEntity.created(URI.create(userUri)).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody com.barbershop.serviceusers.api.dto.User user) {
        final String firstName = user.getFirstName();
        final String lastName = user.getLastName();
        final UserRole role = user.getRole();
        final String phoneNumber = user.getPhoneNumber();

        try {
            userServiceImpl.updateUser(id, firstName, lastName, role, phoneNumber);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
