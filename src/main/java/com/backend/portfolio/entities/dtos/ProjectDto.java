package com.backend.portfolio.entities.dtos;


import java.time.LocalDateTime;
import java.util.List;

import com.backend.portfolio.entities.enums.State;

import lombok.Builder;

@Builder
public record ProjectDto(
        Long id,
        String name,
        String description,
        List<ProjectImageDto> images,
        String urlGitHub,
        String urlWebsite,
        LocalDateTime date_create,
        LocalDateTime date_update,
        State state,
        List<Long> technologies) {

}
