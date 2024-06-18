package com.crocobet.customer_notification_address_facade.controllers;

import com.crocobet.customer_notification_address_facade.model.User;
import com.crocobet.customer_notification_address_facade.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/{username}")
    public User getAllAddressesForCustomer(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/customer/{customerId}")
    public User getAllAddressesForCustomer(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User userDetails) {
        return userService.updateUser(userDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
