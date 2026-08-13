package com.backend.portfolio.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.portfolio.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
boolean existsByName(String name);
}
