package io.github.nicolasyazman.teamtaskmanager.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nicolasyazman.teamtaskmanager.config.WebSecurityConfig;
import io.github.nicolasyazman.teamtaskmanager.entity.Task;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.security.JwtAuthFilter;
import io.github.nicolasyazman.teamtaskmanager.service.ProjectService;
import io.github.nicolasyazman.teamtaskmanager.service.TaskService;
import io.github.nicolasyazman.teamtaskmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class, excludeAutoConfiguration = {WebSecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProjectService projectService;
    
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    
    private final ObjectMapper mapper = new ObjectMapper();

    private final User mockUser = new User();

    public TaskControllerTest() {
        mockUser.setId(1);
        mockUser.setEmail("user@example.com");
    }

    @Test
    void shouldCreateTask() throws Exception {
    	TestingAuthenticationToken authToken = new TestingAuthenticationToken("user@example.com", null, "ROLE_USER");
    	authToken.setAuthenticated(true);
		 SecurityContextHolder.getContext().setAuthentication(authToken);
		 
    	Task task = new Task();
        task.setTitle("Do dishes");
        task.setDescription("Very important chore");
        task.setStatus("TODO");

        when(userService.findByEmail("user@example.com")).thenReturn(mockUser);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(task)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetTasksByProjectId() throws Exception {
    	TestingAuthenticationToken authToken = new TestingAuthenticationToken("user@example.com", null, "ROLE_USER");
    	authToken.setAuthenticated(true);
		 SecurityContextHolder.getContext().setAuthentication(authToken);
		 
		 
    	int projectId = 10;

        Task task = new Task();
        task.setId(1);
        task.setTitle("Code Review");
        task.setProjectId(projectId);
        
        when(userService.findByEmail("user@example.com")).thenReturn(mockUser);
        when(taskService.getTasksByProject(projectId)).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks/project/10"))
        .andDo(print()) // helpful during debugging
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].title").value("Code Review"));
        
        mockMvc.perform(get("/tasks/project/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Code Review"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
		 SecurityContextHolder.getContext().setAuthentication(
			        new TestingAuthenticationToken("user@example.com", null)
			    );
		 
        mockMvc.perform(delete("/tasks/5"))
                .andExpect(status().isNoContent());
    }
}