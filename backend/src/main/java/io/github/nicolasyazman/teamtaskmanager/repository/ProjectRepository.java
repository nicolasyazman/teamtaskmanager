package io.github.nicolasyazman.teamtaskmanager.repository;

import io.github.nicolasyazman.teamtaskmanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByOwnerId(UUID ownerId);
}