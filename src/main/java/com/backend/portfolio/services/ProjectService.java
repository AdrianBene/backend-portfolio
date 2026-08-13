package com.backend.portfolio.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.backend.portfolio.entities.dtos.ProjectDto;

public interface ProjectService {

    ProjectDto createProject(ProjectDto dto,MultipartFile[] images);
    ProjectDto getProjectById(Long id);
    List<ProjectDto> getAllProjects();
    void deleteProject(Long id);
    ProjectDto updateProject(Long id,MultipartFile[] images,ProjectDto projectUpdate);
    

}
