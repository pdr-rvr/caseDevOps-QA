package com.example.demo.service;

import com.example.demo.dto.ItemPlacarDTO;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        aluno = new Usuario("Aluno", new Email("a@a.com"), new Matricula("123456"), new Senha("123"));
        temporada = new Temporada("Temp", "Desc", LocalDate.now(), LocalDate.now().plusDays(1));
        Curso curso = new Curso("Curso", "Desc");
        
        avaliacao = new Avaliacao("Prova", curso);
        List<Opcao> opcoes = new ArrayList<>();
        opcoes.add(new Opcao("Certa", true));
        opcoes.add(new Opcao("Errada", false));
        opcoes.add(new Opcao("Errada", false));
        opcoes.add(new Opcao("Errada", false));
        opcoes.add(new Opcao("Errada", false));
        
        // Mock da questão para simular ID (já que setamos null no construtor)
        Questao questao = mock(Questao.class);
        when(questao.getId()).thenReturn(100L);
        when(questao.getPontos()).thenReturn(10);
        when(questao.getOpcoes()).thenReturn(opcoes);
        
        avaliacao.adicionarQuestao(questao); 
    }

    @Test
    @DisplayName("Deve calcular nota corretamente (10 pontos)")
    void deveCalcularNotaCorreta() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findById(10L)).thenReturn(Optional.of(avaliacao));
        
        SolucaoAvaliacaoDTO solucao = new SolucaoAvaliacaoDTO();
        solucao.setAlunoId(1L);
        
        RespostaQuestaoDTO resp = new RespostaQuestaoDTO();
        resp.setQuestaoId(100L); 
        resp.setIndiceOpcaoEscolhida(0); // Correta
        
        solucao.setRespostas(List.of(resp));

        ResultadoResponseDTO result = gamificacaoService.registrarSolucaoAvaliacao(10L, solucao);

        assertEquals(10, result.getNotaObtida());
        verify(resultadoRepository).save(any(Resultado.class));
        verify(classificacaoRepository).save(any(Classificacao.class));
    }

    // --- Cobertura de Validações Iniciais ---

    @Test
    @DisplayName("Deve falhar se aluno não existe")
    void deveFalharAlunoInexistente() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        SolucaoAvaliacaoDTO dto = new SolucaoAvaliacaoDTO();
        dto.setAlunoId(99L);

        assertThrows(IllegalArgumentException.class, () -> 
            gamificacaoService.registrarSolucaoAvaliacao(10L, dto));
    }

    @Test
    @DisplayName("Deve falhar se avaliação não existe")
    void deveFalharAvaliacaoInexistente() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findById(99L)).thenReturn(Optional.empty());

        SolucaoAvaliacaoDTO dto = new SolucaoAvaliacaoDTO();
        dto.setAlunoId(1L);

        assertThrows(IllegalArgumentException.class, () -> 
            gamificacaoService.registrarSolucaoAvaliacao(99L, dto));
    }

    // --- Cobertura da Lógica de Cálculo (Branches do FOR/IF) ---

    @Test
    @DisplayName("Não deve somar pontos se a resposta estiver errada")
    void naoDeveSomarPontosSeErrar() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findById(10L)).thenReturn(Optional.of(avaliacao));

        SolucaoAvaliacaoDTO solucao = new SolucaoAvaliacaoDTO();
        solucao.setAlunoId(1L);
        
        RespostaQuestaoDTO resp = new RespostaQuestaoDTO();
        resp.setQuestaoId(100L);
        resp.setIndiceOpcaoEscolhida(1); // Errada
        
        solucao.setRespostas(List.of(resp));

        ResultadoResponseDTO result = gamificacaoService.registrarSolucaoAvaliacao(10L, solucao);
        
        assertEquals(0, result.getNotaObtida());
    }

    @Test
    @DisplayName("Deve ignorar se o ID da questão não existir na avaliação")
    void deveIgnorarQuestaoInexistente() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findById(10L)).thenReturn(Optional.of(avaliacao));

        SolucaoAvaliacaoDTO solucao = new SolucaoAvaliacaoDTO();
        solucao.setAlunoId(1L);
        
        RespostaQuestaoDTO resp = new RespostaQuestaoDTO();
        resp.setQuestaoId(999L); // ID que não está no gabarito
        resp.setIndiceOpcaoEscolhida(0);
        
        solucao.setRespostas(List.of(resp));

        ResultadoResponseDTO result = gamificacaoService.registrarSolucaoAvaliacao(10L, solucao);
        
        assertEquals(0, result.getNotaObtida());
    }

    @Test
    @DisplayName("Deve ignorar se o índice da opção for inválido")
    void deveIgnorarIndiceInvalido() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(avaliacaoRepository.findById(10L)).thenReturn(Optional.of(avaliacao));

        SolucaoAvaliacaoDTO solucao = new SolucaoAvaliacaoDTO();
        solucao.setAlunoId(1L);
        
        RespostaQuestaoDTO resp1 = new RespostaQuestaoDTO();
        resp1.setQuestaoId(100L);
        resp1.setIndiceOpcaoEscolhida(-1); // Inválido (menor que 0)

        RespostaQuestaoDTO resp2 = new RespostaQuestaoDTO();
        resp2.setQuestaoId(100L);
        resp2.setIndiceOpcaoEscolhida(10); // Inválido (maior que lista)
        
        solucao.setRespostas(List.of(resp1, resp2));

        ResultadoResponseDTO result = gamificacaoService.registrarSolucaoAvaliacao(10L, solucao);
        
        assertEquals(0, result.getNotaObtida());
    }

    // --- Cobertura do Placar ---

    @Test
    @DisplayName("Deve gerar placar atual")
    void deveGerarPlacar() {
        when(temporadaService.buscarEntidadeTemporadaAtiva()).thenReturn(temporada);
        
        Classificacao c = new Classificacao(aluno, temporada);
        c.adicionarPontos(50);
        when(classificacaoRepository.findByTemporadaOrderByPontuacaoTotalDescUltimaAtualizacaoAsc(temporada))
            .thenReturn(List.of(c));

        List<ItemPlacarDTO> placar = gamificacaoService.gerarPlacarAtual();
        
        assertEquals(1, placar.size());
        assertEquals(50, placar.get(0).getPontuacaoTotal());
    }
}