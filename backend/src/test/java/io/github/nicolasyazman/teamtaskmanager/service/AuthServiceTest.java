package io.github.nicolasyazman.teamtaskmanager.service;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authService = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void shouldReturnTrueWhenCredentialsAreValid() {
        String email = "user@example.com";
        String rawPassword = "password123";
        String encodedPassword = "encoded";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean result = authService.validateLogin(email, rawPassword);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenUserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);
        boolean result = authService.validateLogin("nonexistent@example.com", "any");
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenPasswordDoesNotMatch() {
        String email = "user@example.com";
        String rawPassword = "wrongPassword";
        String encodedPassword = "encoded";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean result = authService.validateLogin(email, rawPassword);
        assertFalse(result);
    }
}
