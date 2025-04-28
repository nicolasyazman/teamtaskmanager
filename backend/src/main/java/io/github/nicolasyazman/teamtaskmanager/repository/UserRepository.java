package io.github.nicolasyazman.teamtaskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.nicolasyazman.teamtaskmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
	
}
