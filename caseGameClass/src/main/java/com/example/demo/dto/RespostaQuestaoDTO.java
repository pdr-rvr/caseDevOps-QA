package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RespostaQuestaoDTO {

    @NotNull(message = "O ID da questão é obrigatório")
    private Long questaoId;

    @NotNull(message = "O índice da opção escolhida é obrigatório")
    @Min(0)
    @Max(4) // Sabemos que são 5 opções (0 a 4)
    private Integer indiceOpcaoEscolhida;
}