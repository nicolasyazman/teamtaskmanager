package io.github.nicolasyazman.teamtaskmanager.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.nicolasyazman.teamtaskmanager.config.WebSecurityConfig;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.security.JwtAuthFilter;
import io.github.nicolasyazman.teamtaskmanager.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = {WebSecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper mapper = new ObjectMapper();
  
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    
 
    @Test
    void shouldCreateUser() throws Exception {
    	// Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        // Act & Assert
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).create(any(User.class));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        // Arrange
    	User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        // Act
        when(userService.search()).thenReturn(Arrays.asList(user1, user2));

        // Assert
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        // Arrange
    	User user = new User();
        user.setId(1);
        user.setUsername("user1");

        // Act
        when(userService.findById(1)).thenReturn(user);

        // Assert
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }
}