package com.agrotis.labor.dto;

import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisUpdateDto {

    @NotNull(message = "O nome do analista não pode ser nulo.")
    private UUID codAnalysis;

    @NotNull(message = "O nome do analista não pode ser nulo.")
    @NotBlank(message = "O nome do analista não pode ser nulo.")
    private String analystName;

    @NotNull(message = "A data não pode ser vazia.")
    private LocalDateTime initialDate;

    @NotNull(message = "A data não pode ser vazia.")
    private LocalDateTime finalDate;

    @Valid
    @NotNull(message = "As informações da propriedade não podem ser vazias.")
    private InfoPropertyDto infoProperty;

    @Valid
    @NotNull(message = "As informações do laboratório não podem ser vazias.")
    private InfoLaboratoryDto infoLaboratory;

    @Nullable
    private String notes;

}
