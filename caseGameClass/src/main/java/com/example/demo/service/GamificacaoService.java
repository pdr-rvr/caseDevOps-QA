package com.example.demo.service;

import com.example.demo.dto.ItemPlacarDTO;
import com.example.demo.dto.RespostaQuestaoDTO;
import com.example.demo.dto.ResultadoResponseDTO;
import com.example.demo.dto.SolucaoAvaliacaoDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GamificacaoService {

    private final TemporadaService temporadaService;
    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ResultadoRepository resultadoRepository;
    private final ClassificacaoRepository classificacaoRepository;

    // Injeção de todas as dependências necessárias
    public GamificacaoService(TemporadaService temporadaService,
                              AvaliacaoRepository avaliacaoRepository,
                              UsuarioRepository usuarioRepository,
                              ResultadoRepository resultadoRepository,
                              ClassificacaoRepository classificacaoRepository) {
        this.temporadaService = temporadaService;
        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.resultadoRepository = resultadoRepository;
        this.classificacaoRepository = classificacaoRepository;
    }

    /**
     * CORE LOOP: Recebe as respostas, corrige, salva histórico e atualiza placar.
     */
    @Transactional
    public ResultadoResponseDTO registrarSolucaoAvaliacao(Long avaliacaoId, SolucaoAvaliacaoDTO solucaoDTO) {
        // 1. Validar Contexto (Temporada, Aluno, Avaliação)
        Temporada temporadaAtual = temporadaService.buscarTemporadaAtiva();

        Usuario aluno = usuarioRepository.findById(solucaoDTO.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada."));

        // 2. Calcular Nota (Lógica de Correção)
        Integer notaFinal = calcularNota(avaliacao, solucaoDTO.getRespostas());

        // 3. Persistir Histórico (Entidade Resultado)
        Resultado resultado = new Resultado(aluno, avaliacao, temporadaAtual, notaFinal);
        resultadoRepository.save(resultado);

        // 4. Atualizar Placar (Entidade Classificação - Upsert)
        atualizarClassificacao(aluno, temporadaAtual, notaFinal);

        // 5. Retornar Feedback
        return new ResultadoResponseDTO(resultado);
    }

    /**
     * Gera o placar da temporada atual para exibição.
     */
    @Transactional(readOnly = true)
    public List<ItemPlacarDTO> gerarPlacarAtual() {
        Temporada temporadaAtual = temporadaService.buscarTemporadaAtiva();

        List<Classificacao> ranking = classificacaoRepository
                .findByTemporadaOrderByPontuacaoTotalDescUltimaAtualizacaoAsc(temporadaAtual);

        return ranking.stream()
                .map(ItemPlacarDTO::new)
                .collect(Collectors.toList());
    }

    // --- Métodos Auxiliares Privados ---

    private Integer calcularNota(Avaliacao avaliacao, List<RespostaQuestaoDTO> respostasAluno) {
        int pontuacaoTotal = 0;

        // Transforma a lista de questões da avaliação em um Map para acesso rápido por ID
        Map<Long, Questao> mapaGabarito = avaliacao.getQuestoes().stream()
                .collect(Collectors.toMap(Questao::getId, Function.identity()));

        for (RespostaQuestaoDTO resposta : respostasAluno) {
            Questao questao = mapaGabarito.get(resposta.getQuestaoId());

            if (questao != null) {
                // Valida se o índice enviado é válido (0 a 4)
                if (resposta.getIndiceOpcaoEscolhida() >= 0 && 
                    resposta.getIndiceOpcaoEscolhida() < questao.getOpcoes().size()) {
                    
                    Opcao opcaoEscolhida = questao.getOpcoes().get(resposta.getIndiceOpcaoEscolhida());
                    
                    if (opcaoEscolhida.isCorreta()) {
                        pontuacaoTotal += questao.getPontos();
                    }
                }
            }
        }
        return pontuacaoTotal;
    }

    private void atualizarClassificacao(Usuario aluno, Temporada temporada, Integer pontosGanhos) {
        // Tenta buscar a classificação existente deste aluno nesta temporada
        Classificacao classificacao = classificacaoRepository
                .findByAlunoAndTemporada(aluno, temporada)
                .orElseGet(() -> new Classificacao(aluno, temporada)); // Se não existe, cria nova com 0 pontos

        // Soma os pontos (o método da entidade atualiza a data também)
        classificacao.adicionarPontos(pontosGanhos);

        classificacaoRepository.save(classificacao);
    }
}