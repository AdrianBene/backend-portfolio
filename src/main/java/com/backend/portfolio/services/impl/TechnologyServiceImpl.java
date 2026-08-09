package com.backend.portfolio.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.portfolio.entities.Technology;
import com.backend.portfolio.entities.dtos.TechnologyCreateDto;
import com.backend.portfolio.entities.dtos.TechnologyDto;
import com.backend.portfolio.exception.ResourceAlreadyExistException;
import com.backend.portfolio.exception.ResourceNotFoundException;
import com.backend.portfolio.respositories.TechnolgyRepository;
import com.backend.portfolio.services.FileStorageService;
import com.backend.portfolio.services.TechnologyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnolgyRepository technolgyRepository;
    private final FileStorageService fileStorageService;

    @Override
    public TechnologyDto createTechnology(TechnologyCreateDto technologyCreateDto, MultipartFile image) {
        if (technolgyRepository.existsByName(technologyCreateDto.name())) {
            throw new ResourceAlreadyExistException(
                    "Ya existe una tecnologia con el nombre " + technologyCreateDto.name());

        }
        String imageName = fileStorageService.saveImage(image);

        Technology technology = Technology.builder()
                .name(technologyCreateDto.name())
                .typeTechnology(technologyCreateDto.typeTechnology())
                .icon(imageName)
                .build();

        Technology saveTechnology = technolgyRepository.save(technology);
        return converDto(saveTechnology);
    }

    @Override
    public List<TechnologyDto> getAllTechnology() {

        return technolgyRepository.findAll().stream().map(technology -> converDto(technology)).toList();

    }

    @Override
    public TechnologyDto getTechnologyById(Long id) {
        Technology technologyfind = technolgyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la tecnologia con id " + id));
        return converDto(technologyfind);

    }

    @Override
    public TechnologyDto updateTechnology(Long id, TechnologyCreateDto technologyCreateDto, MultipartFile image) {

        Technology technology = technolgyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la tecnología con el id " + id));
        if (!technology.getName().equals(technologyCreateDto.name())) {
            if (technolgyRepository.existsByName(technologyCreateDto.name())) {
                throw new ResourceAlreadyExistException(
                        "Ya existe una tecnologia con el nombre " + technologyCreateDto.name());
            }
        }
        technology.setName(technologyCreateDto.name());
        technology.setTypeTechnology(technologyCreateDto.typeTechnology());
        if (image != null && !image.isEmpty()) {

            String newImageName = fileStorageService.saveImage(image);
            fileStorageService.deleteImage(technology.getIcon());
            technology.setIcon(newImageName);

        }
        Technology updateTechnology = technolgyRepository.save(technology);
        return converDto(updateTechnology);

    }

    @Override
    public void deleteTechnology(Long id) {

        Technology technology = technolgyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la tecnologia con el id " + id));

        fileStorageService.deleteImage(technology.getIcon());

        technolgyRepository.delete(technology);

    }

    public TechnologyDto converDto(Technology technology) {

        String imageUrl = null;
        if (technology.getIcon() != null) {
            imageUrl = "http://localhost:8080/uploads/technology/" + technology.getIcon();

        }
        List<Long> projectIds = technology.getProjects().stream().map(project -> project.getId()).toList();

        return new TechnologyDto(technology.getName(), technology.getTypeTechnology(), imageUrl, projectIds);

    }

}
