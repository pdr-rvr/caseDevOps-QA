package com.example.demo.service;

import com.example.demo.dto.RespostaQuestaoDTO;
import com.example.demo.dto.ResultadoResponseDTO;
import com.example.demo.dto.SolucaoAvaliacaoDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GamificacaoServiceTest {

    @Mock private TemporadaService temporadaService;
    @Mock private AvaliacaoRepository avaliacaoRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ResultadoRepository resultadoRepository;
    @Mock private ClassificacaoRepository classificacaoRepository;

    @InjectMocks private GamificacaoService gamificacaoService;

    private Avaliacao avaliacao;
    private Usuario aluno;
    private Temporada temporada;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup Entidades
        aluno = new Usuario("Aluno", new Email("a@a.com"), new Matricula("123456"), new Senha("123"));
        temporada = new Temporada("Temp", "Desc", LocalDate.now(), LocalDate.now().plusDays(1));
        Curso curso = new Curso("Curso", "Desc");
        
        // Cria Avaliação com 1 Questão e 5 Opções
        avaliacao = new Avaliacao("Prova", curso);
        List<Opcao> opcoes = new ArrayList<>();
        opcoes.add(new Opcao("Certa", true)); // Índice 0 = Correta
        opcoes.add(new Opcao("Errada", false)); // Índice 1
        opcoes.add(new Opcao("Errada", false));
        opcoes.add(new Opcao("Errada", false));
        opcoes.add(new Opcao("Errada", false));
        
        Questao questao = new Questao("Questao 1", 10, opcoes);
        avaliacao.adicionarQuestao(questao); 
    }


    @Test
    @DisplayName("Deve calcular nota corretamente (10 pontos)")
    void deveCalcularNotaCorreta() {
        // Dado
        when(temporadaService.buscarTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findById(10L)).thenReturn(Optional.of(avaliacao));
        
        // O Service usa o ID da questão para buscar no mapa. 
        // Como a questão criada no setup tem ID null, o DTO deve mandar null.
        Long idQuestao = avaliacao.getQuestoes().get(0).getId(); // Será null neste teste unitário

        SolucaoAvaliacaoDTO solucao = new SolucaoAvaliacaoDTO();
        solucao.setAlunoId(1L);
        
        RespostaQuestaoDTO resp = new RespostaQuestaoDTO();
        resp.setQuestaoId(idQuestao); 
        resp.setIndiceOpcaoEscolhida(0); // Escolheu a opção 0 (Correta)
        
        solucao.setRespostas(List.of(resp));

        // Quando
        ResultadoResponseDTO result = gamificacaoService.registrarSolucaoAvaliacao(10L, solucao);

        // Então
        assertNotNull(result);
        // Esperamos 10 pontos (acertou a unica questao)
        assertEquals(10, result.getNotaObtida());
        
        // Verifica se salvou histórico e atualizou placar
        verify(resultadoRepository).save(any(Resultado.class));
        verify(classificacaoRepository).save(any(Classificacao.class));
    }
}