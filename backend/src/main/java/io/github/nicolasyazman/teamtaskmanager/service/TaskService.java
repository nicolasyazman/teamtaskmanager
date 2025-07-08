package io.github.nicolasyazman.teamtaskmanager.service;

import io.github.nicolasyazman.teamtaskmanager.entity.Task;
import io.github.nicolasyazman.teamtaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Populates the sample project with sample tasks.
     * @param projectId
     * @param userId
     */
    public void createSampleTasks(int projectId, int userId) {
        List<Task> tasks = List.of(
            new Task("Set up CI/CD pipeline", "Configure automated builds and deployments using GitHub Actions and Docker.", "TODO", 2, LocalDateTime.now().plusDays(3), projectId, userId),
            new Task("Design database schema", "Define and model tables for users, projects, tasks, and permissions.", "TODO", 3, LocalDateTime.now().plusDays(2), projectId, userId),
            new Task("Implement authentication", "Add user login and JWT-based authentication to backend.", "TODO", 2, LocalDateTime.now().plusDays(5), projectId, userId),
            new Task("Create API documentation", "Document REST API endpoints using Swagger/OpenAPI.", "TODO", 1, LocalDateTime.now().plusDays(4), projectId, userId),

            new Task("Add forgot password flow", "Allow users to request a password reset via email.", "BACKLOG", 2, LocalDateTime.now().plusDays(10), projectId, userId),
            new Task("Optimize images", "Compress and lazy-load frontend assets for performance.", "BACKLOG", 1, LocalDateTime.now().plusDays(7), projectId, userId),
            new Task("Add user roles", "Differentiate permissions between admins and contributors.", "BACKLOG", 3, LocalDateTime.now().plusDays(12), projectId, userId),
            new Task("Setup staging environment", "Create separate staging server for testing before production deploys.", "BACKLOG", 2, LocalDateTime.now().plusDays(9), projectId, userId),

            new Task("Implement task board UI", "Build Kanban-style task board with drag-and-drop support.", "PROGRESS", 3, LocalDateTime.now().plusDays(5), projectId, userId),
            new Task("Integrate frontend with backend", "Connect Vue.js app with backend API using Axios.", "PROGRESS", 2, LocalDateTime.now().plusDays(6), projectId, userId),
            new Task("Add project creation form", "Form to create new projects with validation and feedback.", "PROGRESS", 1, LocalDateTime.now().plusDays(4), projectId, userId),
            new Task("Create user profile page", "Show user details and option to update password or email.", "PROGRESS", 2, LocalDateTime.now().plusDays(7), projectId, userId),

            new Task("Unit tests for backend", "Add unit tests for services and controllers in the backend.", "REVIEW", 3, LocalDateTime.now().plusDays(1), projectId, userId),
            new Task("Responsive layout", "Ensure the app looks good on desktop, tablet, and mobile.", "REVIEW", 2, LocalDateTime.now().plusDays(2), projectId, userId),
            new Task("Accessibility improvements", "Improve keyboard navigation and screen reader support.", "REVIEW", 1, LocalDateTime.now().plusDays(2), projectId, userId),
            new Task("Add dark mode", "Provide a toggle to switch between light and dark UI themes.", "REVIEW", 1, LocalDateTime.now().plusDays(3), projectId, userId),

            new Task("Project setup", "Initialized frontend (Vue) and backend (Node.js) repos.", "DONE", 1, LocalDateTime.now().minusDays(5), projectId, userId),
            new Task("Initial commit", "Pushed base project structure with README and Git ignore rules.", "DONE", 1, LocalDateTime.now().minusDays(4), projectId, userId),
            new Task("User registration", "Allow users to sign up with email and password.", "DONE", 2, LocalDateTime.now().minusDays(3), projectId, userId),
            new Task("Dockerize backend", "Added Dockerfile and docker-compose for backend services.", "DONE", 3, LocalDateTime.now().minusDays(2), projectId, userId)
        );

        taskRepository.saveAll(tasks);
    }
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByProject(int projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getTasksByCreator(int userId) {
        return taskRepository.findByCreatedBy(userId);
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
