package com.agrotis.labor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoPropertyDto {

    @NotNull(message = "O id da propriedade não pode ser vazio/invalido.")
    private Long id;

    @NotNull(message = "O nome da propriedade não pode ser vazio.")
    @NotBlank(message = "O nome da propriedade não pode ser vazio.")
    private String name;

    @CNPJ(message = "CNPJ inválido, por favor tente novamente.")
    @NotNull(message = "O CNPJ não pode ser vazio.")
    @NotBlank(message = "O CNPJ não pode ser vazio.")
    private String cnpj;

}
