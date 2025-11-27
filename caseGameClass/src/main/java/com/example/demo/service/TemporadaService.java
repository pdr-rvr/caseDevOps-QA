package com.example.demo.service;

import com.example.demo.dto.TemporadaRequestDTO;
import com.example.demo.dto.TemporadaResponseDTO;
import com.example.demo.entity.Temporada;
import com.example.demo.repository.TemporadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;

    public TemporadaService(TemporadaRepository temporadaRepository) {
        this.temporadaRepository = temporadaRepository;
    }

    /**
     * Cria uma nova temporada.
     */
    @Transactional
    public TemporadaResponseDTO criarTemporada(TemporadaRequestDTO dto) {
        if (temporadaRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Já existe uma temporada com o nome: " + dto.getNome());
        }

        Temporada temporada = new Temporada(
                dto.getNome(),
                dto.getDescricao(),
                dto.getDataInicio(),
                dto.getDataFim()
        );

        temporadaRepository.save(temporada);
        return new TemporadaResponseDTO(temporada);
    }


    @Transactional(readOnly = true)
    public TemporadaResponseDTO buscarTemporadaAtiva() {
        return new TemporadaResponseDTO(buscarEntidadeTemporadaAtiva());
    }

    /**
     * Retorna a ENTIDADE para uso interno de outros serviços (GamificacaoService).
     * Este método contém a lógica real de busca.
     */
    @Transactional(readOnly = true)
    public Temporada buscarEntidadeTemporadaAtiva() {
        Optional<Temporada> ativaOpt = temporadaRepository.findByAtivaTrue();

        if (ativaOpt.isPresent()) {
            Temporada ativa = ativaOpt.get();
            LocalDate hoje = LocalDate.now();
            if (!hoje.isBefore(ativa.getDataInicio()) && !hoje.isAfter(ativa.getDataFim())) {
                return ativa;
            }
        }
        throw new IllegalStateException("Não há nenhuma temporada ativa ou vigente no momento. O placar está fechado.");
    }

    @Transactional(readOnly = true)
    public List<TemporadaResponseDTO> listarTodas() {
        return temporadaRepository.findAll().stream()
                .map(TemporadaResponseDTO::new)
                .collect(Collectors.toList());
    }
}