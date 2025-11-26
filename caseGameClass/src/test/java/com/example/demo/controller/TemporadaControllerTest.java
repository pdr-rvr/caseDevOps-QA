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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TemporadaController.class, 
            excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class TemporadaControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private TemporadaService temporadaService;

    @Test
    @DisplayName("POST /temporadas - Deve criar temporada")
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
}