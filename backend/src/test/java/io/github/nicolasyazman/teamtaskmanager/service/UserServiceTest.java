package io.github.nicolasyazman.teamtaskmanager.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;

public class UserServiceTest {

	private UserService userService;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setUp() {
		this.userRepository = mock(UserRepository.class);
		this.passwordEncoder = mock(PasswordEncoder.class);
		this.userService = new UserService(userRepository, passwordEncoder);
	}
	
	@Test
	void shouldNotCreateUserIfUserAlreadyExistsInDatabase() {
		// Arrange
		String email = "demo@demomail.com";
		User user = new User();
		user.setEmail(email);
		
		// Act
		when(this.userRepository.findByEmail(email)).thenReturn(user);
		
		// Assert
		assert(this.userService.create(user) == false);
	}
	
	@Test
	void shouldCreateUserIfUserDoesNotExistInDatabase() {
		// Arrange
		String email = "newuser@demomail.com";
		String password = "mysupersecretpassword";
		String username = "newuser";
		
		User user = new User();
		user.setEmail(email);
		user.setCreated_at(Date.from(Instant.now()));
		user.setId(1);
		user.setUpdated_at(Date.from(Instant.now()));
		user.setPassword(password); // Encrypting the password
		user.setUsername(username);
		
		this.userRepository.save(user);
		// Act
		when(this.userRepository.findByEmail(email)).thenReturn(null);
		
		// Assert
		assert(this.userService.create(user) == true);
	}
	
	@Test
	void shouldReturnAUserIfUsingFindByIdWithAnExistingId() {
		// Arrange
		int id = 1;
		User user = new User();
		Optional<User> optionalUser = Optional.of(user);
		
		// Act
		when(this.userRepository.findById(id)).thenReturn(optionalUser);
		
		// Assert
		assert(this.userService.findById(id) == user);
	}
	
	@Test
	void shouldReturnNullIfUsingFindByIdWithANonExistingId() {
		// Arrange
		int id = -1;
		Optional<User> nullOptional = Optional.ofNullable(null);
	
		// Act
		when(this.userRepository.findById(id)).thenReturn(nullOptional);
		
		// Assert
		assert(this.userService.findById(id) == null);
	}
	@Test
	void shouldReturnAListOfUsersWhenUsingSearch() {
		// Arrange
		User user1 = new User();
		User user2 = new User();
		
		List<User> listOfUser = new ArrayList<User>();
		listOfUser.add(user1);
		listOfUser.add(user2);
		
		//
		when(this.userRepository.findAll()).thenReturn(listOfUser);
		// Assert
		assert(this.userService.search().size() == 2);
	}
	
}
