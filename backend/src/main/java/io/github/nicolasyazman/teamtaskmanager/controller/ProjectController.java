package io.github.nicolasyazman.teamtaskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.nicolasyazman.teamtaskmanager.service.ProjectService;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
	
	private ProjectService projectService;
	
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@PostMapping()
	public void createProject() {
		
	}
	
	@GetMapping(path = "/userid/{id}")
	public void findProjectByUserId(@PathVariable int userid) {
		
	}
	
}
