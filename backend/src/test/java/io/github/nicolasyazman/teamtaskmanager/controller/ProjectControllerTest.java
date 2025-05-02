package io.github.nicolasyazman.teamtaskmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.nicolasyazman.teamtaskmanager.config.WebSecurityConfig;
import io.github.nicolasyazman.teamtaskmanager.entity.Project;
import io.github.nicolasyazman.teamtaskmanager.service.ProjectService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProjectController.class, excludeAutoConfiguration = {WebSecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private ProjectService projectService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void shouldCreateAProjectWhenUsingAPostRequest() throws Exception {
		Project project = new Project();
		project.setId(1);
		project.setName("Vegetable Wars");
		project.setDescription("A multiplayer game where you play a vegetable in a war to be sovereign of the vegetable kingdom");
		
		mockMvc.perform(post("/project")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(project)))
						.andExpect(status().isCreated());
		
	}
	
	@Test
	void shouldGetAProjectWhenUsingAGetRequestWithASpecificId() throws Exception {
		Project project = new Project();
		String projectName = "Vegetable Wars";
		int projectId = 1;
		
		project.setName(projectName);
		project.setId(projectId);
		project.setDescription("A multiplayer game where you play a vegetable in a war to be sovereign of the vegetable kingdom");
	
		when(this.projectService.findProjectById(projectId)).thenReturn(project);
		
		mockMvc.perform(get("/project/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(projectName));
	}
	
	@Test
	void shouldGetAListProjectWhenUsingAGetRequestWithASpecificOwnerId() throws Exception {
		Project project1 = new Project();
		Project project2 = new Project();
	
		int ownerId = 42;
		
		project1.setName("Vegetable Wars");
		project1.setId(1);
		project1.setDescription("A multiplayer game where you play a vegetable in a war to be sovereign of the vegetable kingdom.");
		project1.setOwnerId(ownerId);
		
		project2.setName("We are legion");
		project2.setId(2);
		project2.setDescription("A website helping learners of all ages master cybersecurity and being more aware of cyberthreats.");
		project2.setOwnerId(ownerId);
		
		List<Project> projects = new ArrayList<Project>();
		projects.add(project1);
		projects.add(project2);
		
		when(this.projectService.findProjectsByUserId(ownerId)).thenReturn(projects);
		
		mockMvc.perform(get("/project/ownerid/42"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}
}
