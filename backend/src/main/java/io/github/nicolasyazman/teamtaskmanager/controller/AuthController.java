package io.github.nicolasyazman.teamtaskmanager.controller;


import io.github.nicolasyazman.teamtaskmanager.dto.ApiResponse;
import io.github.nicolasyazman.teamtaskmanager.dto.LoginRequest;
import io.github.nicolasyazman.teamtaskmanager.security.JwtService;
import io.github.nicolasyazman.teamtaskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
    	this.authService = authService;
    	this.jwtService = jwtService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean success = authService.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());

        
        if (success) {
        	
        	String token = jwtService.generateToken(loginRequest.getEmail());
        	
            return ResponseEntity.ok().body(new ApiResponse("Login successful", token));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse("Invalid credentials", null));
        }
    }
}

