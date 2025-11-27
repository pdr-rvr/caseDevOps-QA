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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotNull(dto1.toString());
        assertEquals("Texto", dto1.getTexto());
        assertTrue(dto1.getCorreta());
    }

    @Test
    @DisplayName("OpcaoResponseDTO: Deve cobrir construtor e getters")
    void testeOpcaoResponseDTO() {
        Opcao entity = new Opcao("Texto", true);
        OpcaoResponseDTO dto = new OpcaoResponseDTO(entity);
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
    @DisplayName("AvaliacaoResponseDTO: Cobertura completa (Setters, Construtor e Null Safety)")
    void testeAvaliacaoResponseDTO() {
        // 1. Teste dos SETTERS e Construtor Vazio (Lombok)
        AvaliacaoResponseDTO dtoManual = new AvaliacaoResponseDTO();
        dtoManual.setId(10L);
        dtoManual.setTitulo("Manual");
        dtoManual.setCursoId(50L);
        dtoManual.setNomeCurso("Curso Manual");
        dtoManual.setQuestoes(new ArrayList<>());

        assertEquals(10L, dtoManual.getId());
        assertEquals("Manual", dtoManual.getTitulo());
        assertEquals(50L, dtoManual.getCursoId());
        assertEquals("Curso Manual", dtoManual.getNomeCurso());
        assertNotNull(dtoManual.getQuestoes());

        // 2. Teste do Construtor com Entidade Completa (Caminho Feliz)
        Curso c = new Curso("Curso Real", "D");
        Avaliacao a = new Avaliacao("Prova Real", c);
        // Adiciona uma questão para testar o mapeamento da lista
        Questao q = new Questao("Q1", 10, criarOpcoesValidas());
        a.adicionarQuestao(q);

        AvaliacaoResponseDTO dtoEntity = new AvaliacaoResponseDTO(a);
        
        assertEquals("Prova Real", dtoEntity.getTitulo());
        assertEquals("Curso Real", dtoEntity.getNomeCurso());
        assertEquals(1, dtoEntity.getQuestoes().size());

        // 3. Teste dos IFs de Null (Branch Missed)
        // Usamos Mock para simular uma entidade retornando null (o que o construtor real proíbe)
        Avaliacao avaliacaoMock = mock(Avaliacao.class);
        when(avaliacaoMock.getId()).thenReturn(99L);
        when(avaliacaoMock.getTitulo()).thenReturn("Mock");
        when(avaliacaoMock.getCurso()).thenReturn(null);    // Força o if(curso != null) ser falso
        when(avaliacaoMock.getQuestoes()).thenReturn(null); // Força o if(questoes != null) ser falso

        AvaliacaoResponseDTO dtoMock = new AvaliacaoResponseDTO(avaliacaoMock);
        
        assertEquals(99L, dtoMock.getId());
        assertEquals("Mock", dtoMock.getTitulo());
        assertNull(dtoMock.getCursoId()); // Confirma que não entrou no if
        assertNull(dtoMock.getQuestoes()); // Confirma que não entrou no if
    }

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