package com.backend.portfolio.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.portfolio.entities.ProjectImage;

public interface ProjectImageRepository extends JpaRepository<ProjectImage,Long>{

    

}
