package io.github.nicolasyazman.teamtaskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void create(User user) {
		// We don't want to create a client in the database if it already exists.
		User userInDB = this.userRepository.findByEmail(user.getEmail());
		if (userInDB == null)
			this.userRepository.save(user);
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
