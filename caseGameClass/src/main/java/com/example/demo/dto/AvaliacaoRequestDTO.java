package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AvaliacaoRequestDTO {

    @NotBlank(message = "O título da avaliação é obrigatório")
    private String titulo;

    @NotNull(message = "O ID do curso é obrigatório")
    private Long cursoId;

    @Valid 
    private List<QuestaoRequestDTO> questoes;
}