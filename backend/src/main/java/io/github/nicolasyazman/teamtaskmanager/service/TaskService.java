package io.github.nicolasyazman.teamtaskmanager.service;

import io.github.nicolasyazman.teamtaskmanager.entity.Task;
import io.github.nicolasyazman.teamtaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
