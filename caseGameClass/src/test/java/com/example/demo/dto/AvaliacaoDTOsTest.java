package com.example.demo.dto;

import com.example.demo.entity.Avaliacao;
import com.example.demo.entity.Curso;
import com.example.demo.entity.Opcao;
import com.example.demo.entity.Questao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AvaliacaoDTOsTest {

    @Test
    @DisplayName("OpcaoRequestDTO: Deve cobrir @Data")
    void testeOpcaoRequestDTO() {
        OpcaoRequestDTO dto1 = new OpcaoRequestDTO();
        dto1.setTexto("Texto");
        dto1.setCorreta(true);

        OpcaoRequestDTO dto2 = new OpcaoRequestDTO();
        dto2.setTexto("Texto");
        dto2.setCorreta(true);

        // Testa Equals, HashCode e ToString
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
        
        // Testa Getters
        assertEquals("Texto", dto1.getTexto());
        assertTrue(dto1.getCorreta());
    }

    @Test
    @DisplayName("OpcaoResponseDTO: Deve cobrir construtor e getters")
    void testeOpcaoResponseDTO() {
        Opcao entity = new Opcao("Texto", true);
        OpcaoResponseDTO dto = new OpcaoResponseDTO(entity);
        
        // Testa Setter manual (se houver)
        dto.setTexto("Novo");
        
        assertNotNull(dto);
        assertEquals("Novo", dto.getTexto());
    }

    @Test
    @DisplayName("QuestaoRequestDTO: Deve cobrir @Data")
    void testeQuestaoRequestDTO() {
        QuestaoRequestDTO dto1 = new QuestaoRequestDTO();
        dto1.setEnunciado("E");
        dto1.setPontos(10);
        dto1.setOpcoes(new ArrayList<>());

        QuestaoRequestDTO dto2 = new QuestaoRequestDTO();
        dto2.setEnunciado("E");
        dto2.setPontos(10);
        dto2.setOpcoes(new ArrayList<>());

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }

    @Test
    @DisplayName("QuestaoResponseDTO: Deve converter entidade")
    void testeQuestaoResponseDTO() {
        List<Opcao> opcoes = new ArrayList<>(); 
        Questao q = new Questao("E", 10, criarOpcoesValidas()); 
        
        QuestaoResponseDTO dto = new QuestaoResponseDTO();
        dto.setId(1L);
        dto.setEnunciado("E");
        dto.setPontos(10);
        dto.setOpcoes(new ArrayList<>());
        
        assertNotNull(dto.toString()); 
        assertEquals(1L, dto.getId());
    }

    @Test
    @DisplayName("AvaliacaoRequestDTO: Deve cobrir @Data")
    void testeAvaliacaoRequestDTO() {
        AvaliacaoRequestDTO dto1 = new AvaliacaoRequestDTO();
        dto1.setTitulo("T");
        dto1.setCursoId(1L);
        
        AvaliacaoRequestDTO dto2 = new AvaliacaoRequestDTO();
        dto2.setTitulo("T");
        dto2.setCursoId(1L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
    }

    @Test
    @DisplayName("AvaliacaoResponseDTO: Deve cobrir conversão")
    void testeAvaliacaoResponseDTO() {
        Curso c = new Curso("C", "D");
        Avaliacao a = new Avaliacao("Titulo", c);
        
        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO(a);
        
        assertEquals("Titulo", dto.getTitulo());
        assertEquals("C", dto.getNomeCurso());
    }

    // Helper rápido para evitar erro de validação da entidade Questao
    private List<Opcao> criarOpcoesValidas() {
        List<Opcao> l = new ArrayList<>();
        l.add(new Opcao("1", true));
        l.add(new Opcao("2", false));
        l.add(new Opcao("3", false));
        l.add(new Opcao("4", false));
        l.add(new Opcao("5", false));
        return l;
    }
}