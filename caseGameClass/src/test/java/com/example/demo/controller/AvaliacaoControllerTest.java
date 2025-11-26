package com.example.demo.controller;

import com.example.demo.dto.AvaliacaoRequestDTO;
import com.example.demo.dto.AvaliacaoResponseDTO;
import com.example.demo.service.AvaliacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AvaliacaoController.class, 
            excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AvaliacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AvaliacaoService avaliacaoService;

    private AvaliacaoResponseDTO responseDTO;
    private AvaliacaoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Dados de resposta Mock
        responseDTO = new AvaliacaoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Prova Java");
        responseDTO.setCursoId(10L);
        
        // Dados de requisição Mock 
        // O @Valid do controller vai reclamar se a lista for nula, então inicializamos
        requestDTO = new AvaliacaoRequestDTO();
        requestDTO.setTitulo("Prova Java");
        requestDTO.setCursoId(10L);
        requestDTO.setQuestoes(new ArrayList<>()); // Lista vazia só para passar na validação @NotNull
    }

    @Test
    @DisplayName("POST /api/avaliacoes - Deve criar avaliação e retornar 201")
    void deveCriarAvaliacao() throws Exception {
        // Dado
        when(avaliacaoService.criarAvaliacao(any(AvaliacaoRequestDTO.class))).thenReturn(responseDTO);

        // Quando & Então
        mockMvc.perform(post("/api/avaliacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Prova Java"));
    }

    @Test
    @DisplayName("GET /api/avaliacoes/curso/{id} - Deve listar avaliações")
    void deveListarPorCurso() throws Exception {
        // Dado
        when(avaliacaoService.listarAvaliacoesPorCurso(10L)).thenReturn(List.of(responseDTO));

        // Quando & Então
        mockMvc.perform(get("/api/avaliacoes/curso/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Prova Java"));
    }

    @Test
    @DisplayName("GET /api/avaliacoes/{id} - Deve buscar por ID")
    void deveBuscarPorId() throws Exception {
        // Dado
        when(avaliacaoService.buscarAvaliacaoPorId(1L)).thenReturn(responseDTO);

        // Quando & Então
        mockMvc.perform(get("/api/avaliacoes/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Prova Java"));
    }
}