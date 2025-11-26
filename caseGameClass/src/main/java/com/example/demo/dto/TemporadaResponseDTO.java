package com.example.demo.dto;

import com.example.demo.entity.Temporada;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TemporadaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativa;

    public TemporadaResponseDTO(Temporada entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.dataInicio = entity.getDataInicio();
        this.dataFim = entity.getDataFim();
        this.ativa = entity.isAtiva();
    }
}