package io.github.nicolasyazman.teamtaskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.nicolasyazman.teamtaskmanager.entity.Project;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.service.ProjectService;
import io.github.nicolasyazman.teamtaskmanager.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
	
	private ProjectService projectService;
	private UserService userService;
	
	public ProjectController(ProjectService projectService, UserService userService) {
		this.projectService = projectService;
		this.userService = userService;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createProject(@RequestBody Project project) {
		String userEmail = getCurrentUserId();
		if (userEmail == null) {
			return;
		}
	    User user = this.userService.findByEmail(userEmail);
	    if (user == null) {
	    	return;
	    }
	    project.setOwnerId(user.getId());
		this.projectService.create(project);
	}
	
	public String getCurrentUserId() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication.getName(); // or use a custom UserDetails object
	}
	
	@GetMapping(path = "/mine") 
	public List<Project> getMyProjects(){
	    String userEmail = getCurrentUserId();
	    User user = this.userService.findByEmail(userEmail);
	    if (user == null) {
	    	return null;
	    }
	    int userId = user.getId();
	    return projectService.findProjectsByUserId(userId);
	}
	
	@GetMapping(path = "/{projectId}")
	public Project findProjectById(@PathVariable int projectId) {
		return this.projectService.findProjectById(projectId);
	}
	
	@GetMapping(path = "/ownerid/{ownerId}")
	public List<Project> findProjectsByOwnerId(@PathVariable int ownerId) {
		return this.projectService.findProjectsByUserId(ownerId);
	}
	
}
