package com.example.demo.service;

import com.example.demo.dto.AvaliacaoRequestDTO;
import com.example.demo.dto.AvaliacaoResponseDTO;
import com.example.demo.dto.OpcaoRequestDTO;
import com.example.demo.dto.QuestaoRequestDTO;
import com.example.demo.entity.Avaliacao;
import com.example.demo.entity.Curso;
import com.example.demo.entity.Opcao;
import com.example.demo.entity.Questao;
import com.example.demo.repository.AvaliacaoRepository;
import com.example.demo.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final CursoRepository cursoRepository;

    // Injeção de dependência via construtor
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, CursoRepository cursoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Cria uma nova avaliação completa (com questões e opções).
     * @Transactional garante que tudo seja salvo ou nada seja salvo (rollback em caso de erro).
     */
    @Transactional
    public AvaliacaoResponseDTO criarAvaliacao(AvaliacaoRequestDTO dto) {
        // 1. Busca o Curso (Regra: Avaliação deve pertencer a um curso existente)
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com ID: " + dto.getCursoId()));

        // 2. Cria a entidade Avaliação (Raiz)
        Avaliacao avaliacao = new Avaliacao(dto.getTitulo(), curso);

        // 3. Converte e Adiciona as Questões 
        // Iteramos sobre os DTOs de questão para criar as entidades
        for (QuestaoRequestDTO questaoDTO : dto.getQuestoes()) {
            
            // Converte a lista de DTOs de opção para VOs Opcao
            List<Opcao> opcoes = converterOpcoes(questaoDTO.getOpcoes());

            // Cria a questão (O construtor da Questão validará as regras de 5 opções/1 correta)
            Questao questao = new Questao(
                    questaoDTO.getEnunciado(),
                    questaoDTO.getPontos(),
                    opcoes
            );

            // O método helper da entidade cuida do vínculo bidirecional
            avaliacao.adicionarQuestao(questao);
        }

        // 4. Salva tudo em cascata
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        // 5. Retorna o DTO de resposta
        return new AvaliacaoResponseDTO(avaliacaoSalva);
    }

    /**
     * Lista todas as avaliações de um curso específico.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorCurso(Long cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new IllegalArgumentException("Curso não encontrado com ID: " + cursoId);
        }

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByCursoId(cursoId);

        return avaliacoes.stream()
                .map(AvaliacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma avaliação pelo ID, retornando todos os detalhes (questões/opções).
     */
    @Transactional(readOnly = true)
    public AvaliacaoResponseDTO buscarAvaliacaoPorId(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Avaliação não encontrada com ID: " + id));

        return new AvaliacaoResponseDTO(avaliacao);
    }

    // --- Métodos Auxiliares Privados (Helpers) ---

    /**
     * Converte uma lista de OpcaoRequestDTO para uma lista de Value Objects Opcao.
     */
    private List<Opcao> converterOpcoes(List<OpcaoRequestDTO> opcoesDTO) {
        return opcoesDTO.stream()
                .map(dto -> new Opcao(dto.getTexto(), dto.getCorreta()))
                .collect(Collectors.toList());
    }
}