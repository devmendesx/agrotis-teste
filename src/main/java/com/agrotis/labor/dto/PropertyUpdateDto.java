package com.agrotis.labor.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyUpdateDto {

    @NotNull(message = "O codigo da propriedade não pode ser vazio.")
    private UUID cod;

    @NotNull(message = "O nome da propriedade não pode ser vazio.")
    @NotBlank(message = "O nome da propriedade não pode ser vazio.")
    private String name;

    @NotNull(message = "O cnpj da propriedade não pode ser vazio.")
    @CNPJ
    private String cnpj;

}
