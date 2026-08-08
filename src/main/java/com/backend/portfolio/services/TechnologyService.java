package com.backend.portfolio.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.portfolio.entities.dtos.TechnologyCreateDto;
import com.backend.portfolio.entities.dtos.TechnologyDto;

public interface TechnologyService {

    TechnologyDto createTechnology( TechnologyCreateDto technologyCreateDto,MultipartFile image);
    List<TechnologyDto> getAllTechnology();
    TechnologyDto getTechnologyById(Long id);
    TechnologyDto updateTechnology(Long id,TechnologyCreateDto technologyCreateDto,MultipartFile image);
    void deleteTechnology(Long id);
    
    

}



    

    


