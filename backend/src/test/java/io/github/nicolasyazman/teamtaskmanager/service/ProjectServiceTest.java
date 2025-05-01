package io.github.nicolasyazman.teamtaskmanager.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.github.nicolasyazman.teamtaskmanager.entity.Project;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.ProjectRepository;
import io.github.nicolasyazman.teamtaskmanager.repository.UserRepository;

public class ProjectServiceTest {

	private ProjectRepository projectRepository;
	private UserRepository userRepository;
	private UserService userService;
	private ProjectService projectService;
	
	@BeforeEach
	void setUp() {
		// Here we will mock our database repository
		this.projectRepository = mock(ProjectRepository.class);
		this.userRepository = mock(UserRepository.class);
		this.userService = mock(UserService.class);
		this.projectService = new ProjectService(this.projectRepository);
	}
	
	@Test
	void shouldFindOneProjectIfOnlyOneProjectMadeByAUserExists() {
		// Arrange
		Project project = new Project();
		int ownerId = 1;
		project.setOwnerId(ownerId);
		
		// Act
		ArrayList<Project> projects = new ArrayList<Project>();
		projects.add(project);
		when(this.projectRepository.findByOwnerId(ownerId)).thenReturn(projects);
		
		// Assert
		assert(projectService.findProjectsByUserId(ownerId) != null);
		assert(projectService.findProjectsByUserId(ownerId).size() == 1);
		
	}
	
	@Test
	void shouldNotFindAProjectIfTheUserIdDoesNotExist() {
		// Arrange
		int invalidUserId = -1;
		
		// Act
		when(this.projectRepository.findByOwnerId(invalidUserId)).thenReturn(null);
		List<Project> projects = this.projectService.findProjectsByUserId(invalidUserId);
		// Assert
		assert(projects == null);
	}
	
	
	
	
}
