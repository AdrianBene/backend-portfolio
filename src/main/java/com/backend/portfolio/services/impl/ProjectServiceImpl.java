package com.backend.portfolio.services.impl;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.portfolio.entities.Project;
import com.backend.portfolio.entities.ProjectImage;
import com.backend.portfolio.entities.Technology;

import com.backend.portfolio.entities.dtos.ProjectDto;
import com.backend.portfolio.entities.dtos.ProjectImageDto;

import com.backend.portfolio.exception.ResourceAlreadyExistException;
import com.backend.portfolio.exception.ResourceNotFoundException;
import com.backend.portfolio.respositories.ProjectRepository;
import com.backend.portfolio.respositories.TechnolgyRepository;
import com.backend.portfolio.services.FileStorageService;
import com.backend.portfolio.services.ProjectService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TechnolgyRepository technolgyRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ProjectDto createProject(ProjectDto dto, MultipartFile[] images) {
        if (projectRepository.existsByName(dto.name())) {
            throw new ResourceAlreadyExistException("Ya existe un proyecto con el nombre: " + dto.name());
            
        }
        Project project = new Project();
        project.setName(dto.name());
        project.setDescription(dto.description());
       project.setUrlGitHub(dto.urlGitHub());
       project.setUrlWebsite(dto.urlWebsite());
       project.setState(dto.state());
       List<Technology> technologies = technolgyRepository.findAllById(dto.technologies());
       project.setTechnologies(technologies);




       if (images != null) {
        for(MultipartFile image : images){
            if (image==null || image.isEmpty()) {
                continue;
            }
            String imageName = fileStorageService.saveImage(image, "projects");
            ProjectImage projectImage = new ProjectImage();
            projectImage.setImage(imageName);
           projectImage.setProject(project);
           project.getImages().add(projectImage);


        }
       }
       Project savedProject = projectRepository.save(project);
       return convertToDto(savedProject);
      


      
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el proyecto con id: " + id));
        return convertToDto(project);

    }

    @Override
    public List<ProjectDto> getAllProjects() {

        return projectRepository.findAll().stream().map(projects -> convertToDto(projects)).toList();

    }

    @Override
    public void deleteProject(Long id) {

        Project deleteProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el proyecto con id: " + id));

        for (ProjectImage projectImage : deleteProject.getImages()) {
            fileStorageService.deleteImage(projectImage.getImage(), "projects");
        }
        projectRepository.delete(deleteProject);

    }

    @Override
    public ProjectDto updateProject(Long id, MultipartFile[] images, ProjectDto dtoProject) {

        Project findProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el proyecto con id " + id));
        if (!findProject.getName().equals(dtoProject.name())
                && projectRepository.existsByName(dtoProject.name())) {
            throw new ResourceAlreadyExistException("Ya existe un proyecto con el nombre" + dtoProject.name());

        }

        findProject.setName(dtoProject.name());
        findProject.setDescription(dtoProject.description());
        findProject.setUrlGitHub(dtoProject.urlGitHub());
        findProject.setUrlWebsite(dtoProject.urlWebsite());
        
         List<Technology> technologies = technolgyRepository.findAllById(dtoProject.technologies());
         findProject.setTechnologies(technologies);


         if (images != null || images.length>0) {
            for(ProjectImage projectImage : findProject.getImages()){
                fileStorageService.deleteImage(projectImage.getImage(), "projects");


            }
            findProject.getImages().clear();

            for(MultipartFile image : images){
                if (image == null || image.isEmpty()) {
                    continue;
                }
                String imageName = fileStorageService.saveImage(image, "projects");
                ProjectImage projectImage = new ProjectImage();

                projectImage.setImage(imageName);
                projectImage.setProject(findProject);


                findProject.getImages().add(projectImage);
            }
            
         }
         Project projectUpdate = projectRepository.save(findProject);

         return convertToDto(projectUpdate);

    }

    private ProjectDto convertToDto(Project project) {
        List<ProjectImageDto> images = project.getImages()
                .stream()
                .map(this::convertImageToDto)
                .toList();

        List<Long> technologies = project.getTechnologies()
                .stream()
                .map(Technology::getId)
                .toList();

        return new ProjectDto(
            project.getId(),
                project.getName(),
                project.getDescription(),
                images,
                project.getUrlGitHub(),
                project.getUrlWebsite(),
                project.getDate_create(),
                project.getDate_update(),
                project.getState(),
                technologies);

    }

    

    private ProjectImageDto convertImageToDto(ProjectImage image) {

        String imageUrl = "http://localhost:8080/uploads/projects/"
                + image.getImage();

        return new ProjectImageDto(

                imageUrl);
    }

}
