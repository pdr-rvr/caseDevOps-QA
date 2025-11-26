package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SolucaoAvaliacaoDTO {

    @NotNull(message = "O ID do aluno é obrigatório")
    private Long alunoId;

    @NotEmpty(message = "Você deve responder as questões")
    @Valid
    private List<RespostaQuestaoDTO> respostas;
}