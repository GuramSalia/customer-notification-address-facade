package com.crocobet.customer_notification_address_facade.services;

import com.crocobet.customer_notification_address_facade.exceptions.UserNotFoundException;
import com.crocobet.customer_notification_address_facade.model.User;
import com.crocobet.customer_notification_address_facade.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByUsername() {
        User user = new User(1L, "username", "password", "ROLE_USER");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("username");

        assertNotNull(foundUser);
        assertEquals("username", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    void testGetUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("username"));
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "username", "password", "ROLE_USER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testAddUser() {
        User user = new User(1L, "username", "password", "ROLE_USER");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User newUser = new User(null, "username", "password", "ROLE_USER");
        User savedUser = userService.addUser(newUser);

        assertNotNull(savedUser);
        assertEquals("username", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User(1L, "username", "password", "ROLE_USER");
        User updatedDetails = new User(null, "newUsername", "newPassword", "ROLE_ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("newUsername", updatedUser.getUsername());
        assertEquals("ROLE_ADMIN", updatedUser.getRole());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User updatedDetails = new User(null, "newUsername", "newPassword", "ROLE_ADMIN");

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updatedDetails));
    }

    @Test
    void testDeleteUser() {
        User user = new User(1L, "username", "password", "ROLE_USER");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}