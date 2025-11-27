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

        curso = new Curso("Curso Java", "Descrição");
        requestDTO = new AvaliacaoRequestDTO();
        requestDTO.setTitulo("Prova 1");
        requestDTO.setCursoId(1L);

        QuestaoRequestDTO questaoDTO = new QuestaoRequestDTO();
        questaoDTO.setEnunciado("Pergunta?");
        questaoDTO.setPontos(10);
        
        List<OpcaoRequestDTO> opcoesDTO = new ArrayList<>();
        for(int i=0; i<5; i++) {
            OpcaoRequestDTO op = new OpcaoRequestDTO();
            op.setTexto("Texto " + i);
            op.setCorreta(i == 0);
            opcoesDTO.add(op);
        }
        questaoDTO.setOpcoes(opcoesDTO);
        
        requestDTO.setQuestoes(List.of(questaoDTO));
    }

    // --- Testes de Criar ---

    @Test
    @DisplayName("Deve criar avaliação com sucesso")
    void deveCriarAvaliacao() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenAnswer(i -> i.getArguments()[0]);

        AvaliacaoResponseDTO response = avaliacaoService.criarAvaliacao(requestDTO);

        assertNotNull(response);
        assertEquals("Prova 1", response.getTitulo());
        assertEquals(1, response.getQuestoes().size());
        verify(avaliacaoRepository, times(1)).save(any(Avaliacao.class));
    }

    @Test
    @DisplayName("Não deve criar avaliação se curso não existe")
    void naoDeveCriarSeCursoNaoExiste() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());
        requestDTO.setCursoId(99L);

        assertThrows(IllegalArgumentException.class, () -> {
            avaliacaoService.criarAvaliacao(requestDTO);
        });
        
        verify(avaliacaoRepository, never()).save(any());
    }
    
    // --- Testes de Listar ---

    @Test
    @DisplayName("Deve listar avaliações por curso")
    void deveListarAvaliacoes() {
        when(cursoRepository.existsById(1L)).thenReturn(true);
        when(avaliacaoRepository.findByCursoId(1L)).thenReturn(List.of(new Avaliacao("Prova 1", curso)));

        List<AvaliacaoResponseDTO> lista = avaliacaoService.listarAvaliacoesPorCurso(1L);

        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Deve lançar erro ao listar se curso não existe")
    void deveLancarErroAoListarSeCursoNaoExiste() {
        when(cursoRepository.existsById(99L)).thenReturn(false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            avaliacaoService.listarAvaliacoesPorCurso(99L);
        });
        assertEquals("Curso não encontrado com ID: 99", ex.getMessage());
    }

    // --- Testes de Buscar por ID ---

    @Test
    @DisplayName("Deve buscar avaliação por ID com sucesso")
    void deveBuscarAvaliacaoPorId() {
        Avaliacao avaliacao = new Avaliacao("Prova Final", curso);
        when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));

        AvaliacaoResponseDTO response = avaliacaoService.buscarAvaliacaoPorId(1L);

        assertNotNull(response);
        assertEquals("Prova Final", response.getTitulo());
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar avaliação inexistente")
    void deveLancarErroAoBuscarInexistente() {
        when(avaliacaoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            avaliacaoService.buscarAvaliacaoPorId(99L);
        });
        assertEquals("Avaliação não encontrada com ID: 99", ex.getMessage());
    }
}