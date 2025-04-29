package io.github.nicolasyazman.teamtaskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void create(User userRaw) {
		// We don't want to create a client in the database if it already exists.
		User userInDB = this.userRepository.findByEmail(userRaw.getEmail());
		if (userInDB == null)
		{
			User user = new User();
			user.setCreated_at(userRaw.getCreated_at());
			user.setEmail(userRaw.getEmail());
			user.setId(userRaw.getId());
			user.setUpdated_at(userRaw.getCreated_at());
			user.setPassword(passwordEncoder.encode(userRaw.getPassword())); // Encrypting the password
			user.setUsername(userRaw.getUsername());
			this.userRepository.save(user);
		}
	}
	
	public List<User> search() {
		return this.userRepository.findAll();
	}
	
	public User findById(int id) {
		Optional<User> optionalUser = this.userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
}
