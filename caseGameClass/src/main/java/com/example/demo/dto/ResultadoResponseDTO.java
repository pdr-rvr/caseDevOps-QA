package com.example.demo.dto;

import com.example.demo.entity.Resultado;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResultadoResponseDTO {

    private Long id;
    private String nomeAluno;
    private String tituloAvaliacao;
    private String nomeTemporada;
    private Integer notaObtida;
    private LocalDateTime dataRealizacao;

    public ResultadoResponseDTO(Resultado entity) {
        this.id = entity.getId();
        this.nomeAluno = entity.getAluno().getNome();
        this.tituloAvaliacao = entity.getAvaliacao().getTitulo();
        this.nomeTemporada = entity.getTemporada().getNome();
        this.notaObtida = entity.getNotaObtida();
        this.dataRealizacao = entity.getDataRealizacao();
    }
}