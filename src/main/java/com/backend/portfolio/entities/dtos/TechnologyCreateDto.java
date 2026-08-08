package com.backend.portfolio.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologyCreateDto(@NotBlank @Size(max = 20,min = 3) String name,@NotBlank @Size(min = 3,max = 20)String typeTechnology) {

}
