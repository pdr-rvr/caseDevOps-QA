package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class QuestaoRequestDTO {

    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    @NotNull(message = "A pontuação é obrigatória")
    @Min(value = 1, message = "A pontuação deve ser pelo menos 1")
    private Integer pontos;

    @NotNull(message = "A lista de opções é obrigatória")
    @Size(min = 5, max = 5, message = "A questão deve ter exatamente 5 opções")
    @Valid 
    private List<OpcaoRequestDTO> opcoes;
}