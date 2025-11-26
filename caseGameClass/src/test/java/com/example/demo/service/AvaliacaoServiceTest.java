package com.example.demo.service;

import com.example.demo.dto.AvaliacaoRequestDTO;
import com.example.demo.dto.AvaliacaoResponseDTO;
import com.example.demo.dto.OpcaoRequestDTO;
import com.example.demo.dto.QuestaoRequestDTO;
import com.example.demo.entity.Avaliacao;
import com.example.demo.entity.Curso;
import com.example.demo.repository.AvaliacaoRepository;
import com.example.demo.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvaliacaoServiceTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    private Curso curso;
    private AvaliacaoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup Dados Básicos
        curso = new Curso("Curso Java", "Descrição");
        
        // Monta o DTO de Request Completo
        requestDTO = new AvaliacaoRequestDTO();
        requestDTO.setTitulo("Prova 1");
        requestDTO.setCursoId(1L);

        // Cria 1 questão válida para o DTO
        QuestaoRequestDTO questaoDTO = new QuestaoRequestDTO();
        questaoDTO.setEnunciado("Pergunta?");
        questaoDTO.setPontos(10);
        
        // Cria 5 opções para o DTO (1 certa, 4 erradas)
        List<OpcaoRequestDTO> opcoesDTO = new ArrayList<>();
        for(int i=0; i<5; i++) {
            OpcaoRequestDTO op = new OpcaoRequestDTO();
            op.setTexto("Texto " + i);
            op.setCorreta(i == 0); // A primeira é correta
            opcoesDTO.add(op);
        }
        questaoDTO.setOpcoes(opcoesDTO);
        
        requestDTO.setQuestoes(List.of(questaoDTO));
    }

    @Test
    @DisplayName("Deve criar avaliação com sucesso")
    void deveCriarAvaliacao() {
        // Dado
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenAnswer(i -> i.getArguments()[0]);

        // Quando
        AvaliacaoResponseDTO response = avaliacaoService.criarAvaliacao(requestDTO);

        // Então
        assertNotNull(response);
        assertEquals("Prova 1", response.getTitulo());
        assertEquals(1, response.getQuestoes().size());
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Não deve criar avaliação se curso não existe")
    void naoDeveCriarSeCursoNaoExiste() {
        // Dado
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());
        requestDTO.setCursoId(99L);

        // Quando & Então
        assertThrows(IllegalArgumentException.class, () -> {
            avaliacaoService.criarAvaliacao(requestDTO);
        });
        
        verify(avaliacaoRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Deve listar avaliações por curso")
    void deveListarAvaliacoes() {
        // Dado
        when(cursoRepository.existsById(1L)).thenReturn(true);
        when(avaliacaoRepository.findByCursoId(1L)).thenReturn(List.of(new Avaliacao("Prova 1", curso)));

        // Quando
        List<AvaliacaoResponseDTO> lista = avaliacaoService.listarAvaliacoesPorCurso(1L);

        // Então
        assertEquals(1, lista.size());
    }
}