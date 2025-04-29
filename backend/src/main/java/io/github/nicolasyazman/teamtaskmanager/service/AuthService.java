package io.github.nicolasyazman.teamtaskmanager.service;

import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    	this.userRepository = userRepository;
    	this.passwordEncoder = passwordEncoder;
    }
    
    public boolean validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
        	return false;
        }
        
        return this.passwordEncoder.matches(password, user.getPassword()); // Simulated password check
       
    }
}