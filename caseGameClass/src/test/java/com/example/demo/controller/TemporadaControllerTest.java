package com.example.demo.controller;

import com.example.demo.dto.TemporadaRequestDTO;
import com.example.demo.dto.TemporadaResponseDTO;
import com.example.demo.service.TemporadaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TemporadaController.class, 
            excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class TemporadaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private TemporadaService temporadaService;

    @Test
    @DisplayName("POST /api/temporadas - Deve criar temporada")
    void deveCriarTemporada() throws Exception {
        TemporadaRequestDTO dto = new TemporadaRequestDTO();
        dto.setNome("T1");
        dto.setDataInicio(LocalDate.now());
        dto.setDataFim(LocalDate.now().plusDays(10));

        when(temporadaService.criarTemporada(any())).thenReturn(new TemporadaResponseDTO());

        mockMvc.perform(post("/api/temporadas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/temporadas/ativa - Deve retornar a temporada ativa")
    void deveBuscarTemporadaAtiva() throws Exception {
        // O Mock agora deve retornar um DTO, não uma Entidade
        TemporadaResponseDTO dtoMock = new TemporadaResponseDTO();
        dtoMock.setNome("Verão 2025");
        dtoMock.setAtiva(true);

        // Ajuste no when(...)
        when(temporadaService.buscarTemporadaAtiva()).thenReturn(dtoMock);

        mockMvc.perform(get("/api/temporadas/ativa")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Verão 2025"))
                .andExpect(jsonPath("$.ativa").value(true));
    }

    @Test
    @DisplayName("GET /api/temporadas - Deve listar todas as temporadas")
    void deveListarTodas() throws Exception {
        // Aqui o service já retorna uma lista de DTOs
        TemporadaResponseDTO dto = new TemporadaResponseDTO();
        dto.setNome("Inverno 2024");

        when(temporadaService.listarTodas()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/temporadas")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Inverno 2024"));
    }
}