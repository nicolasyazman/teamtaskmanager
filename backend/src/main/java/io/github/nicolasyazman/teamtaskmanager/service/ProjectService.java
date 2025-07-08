package io.github.nicolasyazman.teamtaskmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.nicolasyazman.teamtaskmanager.entity.Project;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.ProjectRepository;


@Service
public class ProjectService {

	private ProjectRepository projectRepository;
	
	private TaskService taskService;
	
	public ProjectService(ProjectRepository projectRepository, TaskService taskService) {
		this.projectRepository = projectRepository;
		this.taskService = taskService;
	}
	
	public List<Project> findProjectsByUserId(int userId) {
		return this.projectRepository.findByOwnerId(userId);
	}
	
	public List<Project> findProjectsOfUser(User user) {
		return this.findProjectsByUserId(user.getId());
	}
	
	public Project findProjectById(int projectId) {
		Optional<Project> projectOrNull = this.projectRepository.findById(projectId);
		
		if (projectOrNull.isPresent()) {
			return projectOrNull.get();
		}
		return null;
	}
	
	 /**
	 * Adds a project to the database. Returns the created Project for further operations.
	 * @param project The new project we want to add to the database.
	 * @return The project saved to the database
	 */
	public Project create(Project project) {
		return this.projectRepository.save(project);
	}

	/**
	 * Creates the sample project and populates it with sample tasks
	 * @param userId The owner of this project
	 */
	public void createSampleProject(int userId) {
		Project sampleProject = new Project();
		sampleProject.setDescription("A project that is used for demonstration purposes.");
		sampleProject.setName("Sample Project");
		sampleProject.setOwnerId(userId);
		sampleProject.setCreatedAt(LocalDateTime.now());
		
		sampleProject = this.projectRepository.save(sampleProject);
		int sampleProjectId = sampleProject.getId();
				
		this.taskService.createSampleTasks(sampleProjectId, userId);
	}
}
