package com.backend.portfolio.entities.dtos;

import java.lang.Thread.State;
import java.time.LocalDateTime;
import java.util.List;

public record ProjectDto(
        String name,
        String description,
        List<ProjectImageDto> images,
        String GitHub,
        String Website,
        LocalDateTime date_create,
        LocalDateTime date_update,
        State state,
        List<TechnologyDto> technologies) {

}
