package com.agrotis.labor.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryDto {

    @NotNull(message = "O nome do laboratório não pode ser vazio!")
    @NotBlank(message = "O nome do laboratório não pode ser vazio!")
    private String name;

}


