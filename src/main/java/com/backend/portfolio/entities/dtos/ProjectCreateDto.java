package com.backend.portfolio.entities.dtos;

import java.util.List;

import com.backend.portfolio.entities.enums.State;

public record ProjectCreateDto(
        Long id,
        String name,
        String description,
        String urlGitHub,
        String urlWebsite,
        List<Long> technologies,
        State state) {

}
