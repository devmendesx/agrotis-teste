package com.agrotis.labor.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyViewDto {

    private Long id;
    private UUID codProperty;
    private String name;
    private String cnpj;

}
