package com.agrotis.labor.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LaboratoryListViewDto {
    public Long id;
    public String name;
}
