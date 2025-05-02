package io.github.nicolasyazman.teamtaskmanager.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	@Test
	void shouldNotFindAProjectIfTheUserDoesNotExist() {
		// Arrange
		int invalidUserId = -1;
		User user = new User();
		user.setId(invalidUserId);
		
		// Act
		when(this.projectRepository.findByOwnerId(invalidUserId)).thenReturn(null);
		List<Project> projects = this.projectService.findProjectsOfUser(user);
		// Assert
		assert(projects == null);
	}
	
	@Test
	void findProjectByProjectId_shouldReturnProjectWhenProjectExists() {
		// Arrange
		int projectId = 1;
		Project project = new Project();
		project.setId(projectId);
		
		Optional<Project> optionalProject = Optional.of(project);
		// Act
		when(this.projectRepository.findById(projectId)).thenReturn(optionalProject);
		
		// Assert
		assert(this.projectService.findProjectById(projectId) == optionalProject.get());
	}
	
	@Test
	void findProjectByProjectId_shouldReturnNullWhenProjectDoesNotExist() {
		// Arrange
		int projectId = -1;
		
		Optional<Project> optionalNull = Optional.ofNullable(null);
		// Act
		when(this.projectRepository.findById(projectId)).thenReturn(optionalNull);
		
		// Assert
		assert(this.projectService.findProjectById(projectId) == null);
	}
	
	@Test
	void create_shouldAddTheProjectToTheDatabase() {
		// Arrange
		Project project = new Project();
		
		// Act
		when(this.projectRepository.save(project)).thenReturn(project);
		Project p = this.projectService.create(project);
		
		// Assert
		assert(p == project);
	}
	
}
