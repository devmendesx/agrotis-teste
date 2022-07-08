package com.agrotis.labor.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class LaboratoryViewDto {
    public Long id;
    public UUID cod;
    public String name;
}
