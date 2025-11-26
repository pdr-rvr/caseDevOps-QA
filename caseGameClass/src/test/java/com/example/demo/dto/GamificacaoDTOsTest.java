package com.example.demo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GamificacaoDTOsTest {

    @Test
    void testeRespostaQuestaoDTO() {
        RespostaQuestaoDTO dto1 = new RespostaQuestaoDTO();
        dto1.setQuestaoId(1L);
        dto1.setIndiceOpcaoEscolhida(0);

        RespostaQuestaoDTO dto2 = new RespostaQuestaoDTO();
        dto2.setQuestaoId(1L);
        dto2.setIndiceOpcaoEscolhida(0);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }

    @Test
    void testeSolucaoAvaliacaoDTO() {
        SolucaoAvaliacaoDTO dto1 = new SolucaoAvaliacaoDTO();
        dto1.setAlunoId(1L);
        dto1.setRespostas(List.of());

        SolucaoAvaliacaoDTO dto2 = new SolucaoAvaliacaoDTO();
        dto2.setAlunoId(1L);
        dto2.setRespostas(List.of());

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }

    @Test
    void testeResultadoResponseDTO() {
        // Teste POJO básico (Getters/Setters)
        ResultadoResponseDTO dto = new ResultadoResponseDTO();
        dto.setId(1L);
        dto.setNotaObtida(100);
        dto.setNomeAluno("Aluno");
        
        assertEquals(100, dto.getNotaObtida());
        assertEquals("Aluno", dto.getNomeAluno());
    }

    @Test
    void testeItemPlacarDTO() {
        ItemPlacarDTO dto = new ItemPlacarDTO();
        dto.setNomeAluno("João");
        dto.setPontuacaoTotal(500);
        
        assertEquals("João", dto.getNomeAluno());
        assertEquals(500, dto.getPontuacaoTotal());
    }
}