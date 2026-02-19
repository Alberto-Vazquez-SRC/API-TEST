package com.juan.usersapi.controller;

import com.juan.usersapi.model.User;
import com.juan.usersapi.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.juan.usersapi.dto.LoginRequest;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ) {

        if (filter != null) {
            return service.filterUsers(filter);
        }

        return service.getAllUsers(sortedBy);
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(
                request.getEmail(),
                request.getPassword()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        service.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(
            @PathVariable UUID id,
            @RequestBody User user) {

        return service.updateUser(id, user);
    }

}
