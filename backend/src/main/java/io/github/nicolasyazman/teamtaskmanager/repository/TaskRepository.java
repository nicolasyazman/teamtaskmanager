package io.github.nicolasyazman.teamtaskmanager.repository;

import io.github.nicolasyazman.teamtaskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByProjectId(int projectId);
    List<Task> findByCreatedBy(int userId);
}