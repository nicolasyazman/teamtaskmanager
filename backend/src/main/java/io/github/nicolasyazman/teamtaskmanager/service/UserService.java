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

	/**
	 * A password encoder. The same that was used to encode the user password when adding them to the database.
	 */
	private PasswordEncoder passwordEncoder;
	
	/**
	 * The repository that acts as link to the database, allowing it to make requests (SELECT, ...)
	 */
	private UserRepository userRepository;

	/**
	 * Constructor of the UserService class.
	 * @param userRepository The repository that acts as link to the database (is injected by Spring Boot)
	 * @param passwordEncoder An encoder for the password.
	 */
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * Adds a user to the database if it does not already exists there.
	 * @param userRaw The user we want to add to the database.
	 * @return True if the user was created, false if it already exists.
	 */
	public boolean create(User userRaw) {
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
			return true;
		}
		return false;
	}
	
	/**
	 * Gets all the users in the database.
	 * @return All the users in the database.
	 */
	public List<User> search() {
		return this.userRepository.findAll();
	}
	
	/**
	 * Get a user with a specific id.
	 * @param id The id of the user we want to get.
	 * @return If the id does exist in the database, the user. Otherwise a null value.
	 */
	public User findById(int id) {
		Optional<User> optionalUser = this.userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
}
