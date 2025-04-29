package io.github.nicolasyazman.teamtaskmanager.service;

import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    
    public boolean validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
        	return false;
        }
        return user.getPassword().equals(password); // Simulated password check
       
    }
}