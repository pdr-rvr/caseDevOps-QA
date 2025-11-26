package com.example.demo.dto;

import com.example.demo.entity.Questao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QuestaoResponseDTO {

    private Long id;
    private String enunciado;
    private Integer pontos;
    private List<OpcaoResponseDTO> opcoes;

    // Construtor de mapeamento (Entity -> DTO)
    public QuestaoResponseDTO(Questao entity) {
        this.id = entity.getId();
        this.enunciado = entity.getEnunciado();
        this.pontos = entity.getPontos();
        // Converte a lista de entidades Opcao para lista de DTOs
        this.opcoes = entity.getOpcoes().stream()
                .map(OpcaoResponseDTO::new)
                .collect(Collectors.toList());
    }
}