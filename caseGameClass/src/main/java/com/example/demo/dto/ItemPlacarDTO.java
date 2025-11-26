package com.example.demo.dto;

import com.example.demo.entity.Classificacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ItemPlacarDTO {

    private String nomeAluno;
    private String matriculaAluno; 
    private Integer pontuacaoTotal;
    private LocalDateTime ultimaAtualizacao;

    public ItemPlacarDTO(Classificacao entity) {
        this.nomeAluno = entity.getAluno().getNome();
        this.matriculaAluno = entity.getAluno().getMatricula().getRa();
        this.pontuacaoTotal = entity.getPontuacaoTotal();
        this.ultimaAtualizacao = entity.getUltimaAtualizacao();
    }
}