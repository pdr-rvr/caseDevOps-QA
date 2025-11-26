package com.example.demo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TemporadaRequestDTO {

    @NotBlank(message = "O nome da temporada é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    @Future(message = "A data de fim deve ser no futuro")
    private LocalDate dataFim;
}