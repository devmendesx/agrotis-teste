package com.agrotis.labor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisViewDto {
    private String analystName;
    private UUID codAnalysis;
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private InfoPropertyDto infoProperty;
    private InfoLaboratoryDto infoLaboratory;
    private String notes;
}
