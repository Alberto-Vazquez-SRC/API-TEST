package com.juan.usersapi.service;

import com.juan.usersapi.model.Address;
import com.juan.usersapi.model.User;
import org.springframework.stereotype.Service;
import com.juan.usersapi.util.ValidationUtil;
import com.juan.usersapi.util.AESUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        initializeUsers();
    }

    private void initializeUsers() {

        List<Address> addresses = List.of(
                new Address(1L, "workaddress", "street No. 1", "UK"),
                new Address(2L, "homeaddress", "street No. 2", "AU")
        );

        users.add(new User(
                UUID.randomUUID(),
                "user1@mail.com",
                "user1",
                "+15555555555",
                "password123",
                "AARR990101XXX",
                LocalDateTime.now(ZoneId.of("Indian/Antananarivo")),
                addresses
        ));
    }

    public List<User> getAllUsers(String sortedBy) {

        if (sortedBy == null || sortedBy.isEmpty()) {
            return users;
        }

        Comparator<User> comparator;

        switch (sortedBy) {
            case "email":
                comparator = Comparator.comparing(User::getEmail);
                break;
            case "name":
                comparator = Comparator.comparing(User::getName);
                break;
            case "phone":
                comparator = Comparator.comparing(User::getPhone);
                break;
            case "tax_id":
                comparator = Comparator.comparing(User::getTaxId);
                break;
            case "created_at":
                comparator = Comparator.comparing(User::getCreatedAt);
                break;
            case "id":
                comparator = Comparator.comparing(User::getId);
                break;
            default:
                return users;
        }

        return users.stream()
                .sorted(comparator)
                .toList();
    }

    public List<User> filterUsers(String filter) {

        if (filter == null || filter.isEmpty()) {
            return users;
        }

        String[] parts = filter.split("\\+");

        if (parts.length != 3) {
            return users;
        }

        String field = parts[0];
        String operator = parts[1];
        String value = parts[2];

        return users.stream()
                .filter(user -> matches(user, field, operator, value))
                .toList();
    }

    private boolean matches(User user, String field, String operator, String value) {

        String fieldValue = switch (field) {
            case "email" -> user.getEmail();
            case "name" -> user.getName();
            case "phone" -> user.getPhone();
            case "tax_id" -> user.getTaxId();
            case "created_at" -> user.getCreatedAt().toString();
            case "id" -> user.getId().toString();
            default -> null;
        };

        if (fieldValue == null) return false;

        return switch (operator) {
            case "eq" -> fieldValue.equals(value);
            case "co" -> fieldValue.contains(value);
            case "sw" -> fieldValue.startsWith(value);
            case "ew" -> fieldValue.endsWith(value);
            default -> false;
        };
    }

    public User createUser(User user) {

        // Validar RFC formato
        if (!ValidationUtil.isValidRFC(user.getTaxId())) {
            throw new RuntimeException("Invalid RFC format");
        }

        // Validar Phone formato AndresFormat
        if (!ValidationUtil.isValidPhone(user.getPhone())) {
            throw new RuntimeException("Invalid phone format");
        }

        // Validar tax_id único
        boolean exists = users.stream()
                .anyMatch(u -> u.getTaxId().equals(user.getTaxId()));

        if (exists) {
            throw new RuntimeException("tax_id already exists");
        }
        // ENCRIPTAR PASSWORD
        user.setPassword(
                AESUtil.encrypt(user.getPassword())
        );

        // Generar ID
        user.setId(UUID.randomUUID());

        // Fecha Madagascar
        user.setCreatedAt(
                LocalDateTime.now(ZoneId.of("Indian/Antananarivo"))
        );

        users.add(user);
        return user;
    }

    public String login(String email, String password) {

        User user = users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String decryptedPassword =
                AESUtil.decrypt(user.getPassword());

        if (!decryptedPassword.equals(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid credentials"
            );
        }

        return "Login successful";
    }

    public void deleteUser(UUID id) {

        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        users.remove(user);
    }

    public User updateUser(UUID id, User updatedUser) {

        User existingUser = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found"
                        ));

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getPhone() != null) {

            if (!ValidationUtil.isValidPhone(updatedUser.getPhone())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid phone format"
                );
            }

            existingUser.setPhone(updatedUser.getPhone());
        }

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(
                    AESUtil.encrypt(updatedUser.getPassword())
            );
        }

        return existingUser;
    }





}
