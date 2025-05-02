package io.github.nicolasyazman.teamtaskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.nicolasyazman.teamtaskmanager.entity.Project;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.repository.ProjectRepository;


@Service
public class ProjectService {

	private ProjectRepository projectRepository;
	
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
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
}
