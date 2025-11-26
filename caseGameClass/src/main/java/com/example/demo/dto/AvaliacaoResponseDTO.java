package com.example.demo.dto;

import com.example.demo.entity.Avaliacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AvaliacaoResponseDTO {

    private Long id;
    private String titulo;
    private Long cursoId;
    private String nomeCurso; // Útil para o frontend não precisar buscar o curso separado
    private List<QuestaoResponseDTO> questoes;

    // Construtor de mapeamento (Entity -> DTO)
    public AvaliacaoResponseDTO(Avaliacao entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        
        if (entity.getCurso() != null) {
            this.cursoId = entity.getCurso().getId();
            this.nomeCurso = entity.getCurso().getNome();
        }

        // Converte a lista de questões se houver
        if (entity.getQuestoes() != null) {
            this.questoes = entity.getQuestoes().stream()
                    .map(QuestaoResponseDTO::new)
                    .collect(Collectors.toList());
        }
    }
}