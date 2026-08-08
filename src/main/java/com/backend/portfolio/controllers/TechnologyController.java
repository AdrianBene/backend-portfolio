package com.backend.portfolio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.portfolio.entities.dtos.TechnologyCreateDto;
import com.backend.portfolio.entities.dtos.TechnologyDto;
import com.backend.portfolio.services.TechnologyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/technologies")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TechnologyDto> createTechnology(@Valid
            @RequestPart("technology") TechnologyCreateDto technologyCreateDto,
            @RequestPart(value = "icon", required = false) MultipartFile icon) {
        TechnologyDto technology = technologyService.createTechnology(technologyCreateDto, icon);
        return ResponseEntity.status(HttpStatus.CREATED).body(technology);
    }

    @GetMapping
    public ResponseEntity<List<TechnologyDto>> getAllTechnologies() {
        return ResponseEntity.ok(technologyService.getAllTechnology());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnologyDto> getTechnologyById(@PathVariable Long id) {
        return ResponseEntity.ok(technologyService.getTechnologyById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TechnologyDto> updateTechnology(@PathVariable Long id,
            @RequestPart("technology") TechnologyCreateDto technology,
            @RequestPart(value = "icon", required = false) MultipartFile icon) {
        TechnologyDto technologyDto = technologyService.updateTechnology(id, technology, icon);
        return ResponseEntity.ok(technologyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Long id) {
        technologyService.deleteTechnology(id);
        return ResponseEntity.noContent().build();
    }
}
