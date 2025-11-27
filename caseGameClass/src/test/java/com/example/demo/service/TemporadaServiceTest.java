package com.example.demo.service;

import com.example.demo.dto.TemporadaRequestDTO;
import com.example.demo.dto.TemporadaResponseDTO;
import com.example.demo.entity.Temporada;
import com.example.demo.repository.TemporadaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TemporadaServiceTest {

    @Mock
    private TemporadaRepository temporadaRepository;

    @InjectMocks
    private TemporadaService temporadaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- Testes de criarTemporada ---

    @Test
    @DisplayName("Deve criar temporada com sucesso")
    void deveCriarTemporada() {
        TemporadaRequestDTO dto = new TemporadaRequestDTO();
        dto.setNome("Nova");
        dto.setDataInicio(LocalDate.now());
        dto.setDataFim(LocalDate.now().plusDays(10));

        when(temporadaRepository.existsByNome("Nova")).thenReturn(false);
        when(temporadaRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        TemporadaResponseDTO res = temporadaService.criarTemporada(dto);
        assertNotNull(res);
        assertEquals("Nova", res.getNome());
    }

    @Test
    @DisplayName("Não deve criar temporada com nome duplicado")
    void naoDeveCriarComNomeDuplicado() {
        TemporadaRequestDTO dto = new TemporadaRequestDTO();
        dto.setNome("Duplicada");
        dto.setDataInicio(LocalDate.now());
        dto.setDataFim(LocalDate.now().plusDays(10));

        // Simula que já existe
        when(temporadaRepository.existsByNome("Duplicada")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            temporadaService.criarTemporada(dto);
        });
        assertEquals("Já existe uma temporada com o nome: Duplicada", ex.getMessage());
    }

    // --- Testes de buscarTemporadaAtiva ---

   @Test
    @DisplayName("Deve retornar temporada ativa se estiver dentro do prazo")
    void deveRetornarTemporadaAtivaValida() {
        Temporada temporadaValida = new Temporada(
                "Ativa", "Desc",
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        when(temporadaRepository.findByAtivaTrue()).thenReturn(Optional.of(temporadaValida));

        // O retorno agora é DTO
        TemporadaResponseDTO resultado = temporadaService.buscarTemporadaAtiva();
        
        assertNotNull(resultado);
        assertEquals("Ativa", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar erro se não houver nenhuma temporada marcada como ativa")
    void deveLancarErroSemFlagAtiva() {
        when(temporadaRepository.findByAtivaTrue()).thenReturn(Optional.empty());
        
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            temporadaService.buscarTemporadaAtiva();
        });
        assertEquals("Não há nenhuma temporada ativa ou vigente no momento. O placar está fechado.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro se a temporada ativa estiver fora do prazo (Vencida)")
    void deveLancarErroSeTemporadaAtivaEstiverVencida() {
        // Cria uma temporada que já acabou (Datas no passado)
        Temporada temporadaVencida = new Temporada(
                "Vencida", "Desc",
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(5)
        );

        when(temporadaRepository.findByAtivaTrue()).thenReturn(Optional.of(temporadaVencida));

        // Embora exista a flag 'ativa', a data é inválida, então deve cair no throw final
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            temporadaService.buscarTemporadaAtiva();
        });
        assertEquals("Não há nenhuma temporada ativa ou vigente no momento. O placar está fechado.", ex.getMessage());
    }

    // --- Testes de listarTodas ---

    @Test
    @DisplayName("Deve listar todas as temporadas")
    void deveListarTodas() {
        Temporada t1 = new Temporada("T1", "D", LocalDate.now(), LocalDate.now().plusDays(1));
        Temporada t2 = new Temporada("T2", "D", LocalDate.now(), LocalDate.now().plusDays(1));

        when(temporadaRepository.findAll()).thenReturn(List.of(t1, t2));

        List<TemporadaResponseDTO> lista = temporadaService.listarTodas();
        
        assertEquals(2, lista.size());
        assertEquals("T1", lista.get(0).getNome());
    }

    @Test
    @DisplayName("Deve lançar erro se a temporada ativa for futura (ainda não começou)")
    void deveLancarErroSeTemporadaAtivaForFutura() {
        // Cria uma temporada que começa amanhã
        Temporada temporadaFutura = new Temporada(
                "Futura", "Desc",
                LocalDate.now().plusDays(1), // Início Amanhã
                LocalDate.now().plusDays(30) // Fim daqui a 30 dias
        );

        when(temporadaRepository.findByAtivaTrue()).thenReturn(Optional.of(temporadaFutura));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            temporadaService.buscarTemporadaAtiva();
        });
        assertEquals("Não há nenhuma temporada ativa ou vigente no momento. O placar está fechado.", ex.getMessage());
    }
}