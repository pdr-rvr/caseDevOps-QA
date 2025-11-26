package com.example.demo.dto;

import com.example.demo.entity.Opcao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpcaoResponseDTO {

    private String texto;
    private boolean correta;

    // Construtor de mapeamento (Entity -> DTO)
    public OpcaoResponseDTO(Opcao entity) {
        this.texto = entity.getTexto();
        this.correta = entity.isCorreta();
    }
}