package com.example.demo.service;

import com.example.demo.dto.TemporadaRequestDTO;
import com.example.demo.dto.TemporadaResponseDTO;
import com.example.demo.repository.TemporadaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TemporadaServiceTest {

    @Mock private TemporadaRepository temporadaRepository;
    @InjectMocks private TemporadaService temporadaService;

    @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); }

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
    }

    @Test
    @DisplayName("Deve lançar erro se não houver temporada ativa")
    void deveLancarErroSemTemporadaAtiva() {
        when(temporadaRepository.findByAtivaTrue()).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> temporadaService.buscarTemporadaAtiva());
    }
}