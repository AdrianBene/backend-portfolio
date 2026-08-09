package com.backend.portfolio.entities.dtos;

import java.util.List;

import com.backend.portfolio.entities.enums.State;

public record ProjectCreateDto(
        String name,
        String description,
        String urlGitHub,
        String website,
        List<Long> technologies,
        State state) {

}
