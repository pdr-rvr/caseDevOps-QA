package com.example.demo.controller;

import com.example.demo.dto.ItemPlacarDTO;
import com.example.demo.dto.RespostaQuestaoDTO;
import com.example.demo.dto.ResultadoResponseDTO;
import com.example.demo.dto.SolucaoAvaliacaoDTO;
import com.example.demo.service.GamificacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GamificacaoController.class, 
            excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class GamificacaoControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private GamificacaoService gamificacaoService;

    @Test
    @DisplayName("POST /resolver - Deve retornar 201 Created e Nota")
    void deveResolverAvaliacao() throws Exception {
        // Dado
        SolucaoAvaliacaoDTO dto = new SolucaoAvaliacaoDTO();
        dto.setAlunoId(1L);
        RespostaQuestaoDTO resp = new RespostaQuestaoDTO();
        resp.setQuestaoId(1L);
        resp.setIndiceOpcaoEscolhida(0);
        dto.setRespostas(List.of(resp));

        ResultadoResponseDTO resultMock = new ResultadoResponseDTO();
        resultMock.setNotaObtida(100);

        when(gamificacaoService.registrarSolucaoAvaliacao(eq(10L), any())).thenReturn(resultMock);

        // Quando & Então
        mockMvc.perform(post("/api/gamificacao/avaliacoes/10/resolver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /placar - Deve retornar 200 OK")
    void deveListarPlacar() throws Exception {
        when(gamificacaoService.gerarPlacarAtual()).thenReturn(List.of(new ItemPlacarDTO()));

        mockMvc.perform(get("/api/gamificacao/placar")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}