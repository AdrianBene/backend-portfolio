package com.backend.portfolio.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologyDto(@NotBlank(message = "Debes introducir un nombre")@Size(max = 20, min = 3,message ="El nombre debe tener entre 3 a 20 caracteres" ) String name
,@NotBlank(message = "Debes introducir un tipo de technologia")String typeTechnology
,String icon) {

}
