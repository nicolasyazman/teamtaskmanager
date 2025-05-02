package io.github.nicolasyazman.teamtaskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.nicolasyazman.teamtaskmanager.config.WebSecurityConfig;
import io.github.nicolasyazman.teamtaskmanager.dto.LoginRequest;
import io.github.nicolasyazman.teamtaskmanager.security.JwtAuthFilter;
import io.github.nicolasyazman.teamtaskmanager.security.JwtService;
import io.github.nicolasyazman.teamtaskmanager.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {WebSecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)

class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;
    
    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOkWithTokenOnSuccessfulLogin() throws Exception {
        // Arrange
        String email = "existinguser@mailbox.com";
        String password = "correctPassword";
        String token = "tokenofexistinguser";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        when(authService.validateLogin(email, password)).thenReturn(true);
        when(jwtService.generateToken(email)).thenReturn(token);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Login successful"))
            .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void shouldReturnUnauthorizedOnInvalidLogin() throws Exception {
        // Arrange
        String email = "wrong@mailbox.com";
        String password = "wrongPassword";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        when(authService.validateLogin(email, password)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Invalid credentials"))
            .andExpect(jsonPath("$.token").doesNotExist());
    }
}
