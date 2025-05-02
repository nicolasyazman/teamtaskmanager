package io.github.nicolasyazman.teamtaskmanager.controller;

import io.github.nicolasyazman.teamtaskmanager.entity.Task;
import io.github.nicolasyazman.teamtaskmanager.entity.User;
import io.github.nicolasyazman.teamtaskmanager.service.ProjectService;
import io.github.nicolasyazman.teamtaskmanager.service.TaskService;
import io.github.nicolasyazman.teamtaskmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final ProjectService projectService;
    
    public TaskController(TaskService taskService, UserService userService, ProjectService projectService) {
        this.taskService = taskService;
        this.userService = userService;
        this.projectService = projectService;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return null;
        return userService.findByEmail(authentication.getName());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody Task task) {
        User user = getCurrentUser();
        if (user == null) return;

        task.setCreatedBy(user.getId());
        taskService.create(task);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable int projectId) {
        User user = getCurrentUser();
        if (user == null) return null;
        
        // IMPORTANT : Add check here to verify the user is allowed to see this project
        // For this we shall need a PROJECT_CONTRIBUTOR table
        // This PROJECT_CONTRIBUTOR will link a USER.ID to a PROJECT.ID as foreign key
        // constraint
        return taskService.getTasksByProject(projectId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable int id) {
        User user = getCurrentUser();
        if (user == null) return;

        // IMPORTANT : check if user is allowed to delete
        taskService.deleteTask(id);
    }
}
