package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UsuarioRequestDTO;
import dto.UsuarioResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import service.UsuarioService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 1. Carrega apenas a camada Web (Controller) 
@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    // 2. Ferramenta para simular requisições HTTP
    @Autowired
    private MockMvc mockMvc;

    // 3. Adiciona um "dublê" do Service no contexto do Spring
    @MockBean
    private UsuarioService usuarioService;

    // 4. Helper para converter Objetos Java <-> JSON
    @Autowired
    private ObjectMapper objectMapper;

    // --- Dados de Teste ---
    private UsuarioRequestDTO requestDTO;
    private UsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("Usuario Teste");
        requestDTO.setEmail("teste@dominio.com");
        requestDTO.setMatricula("123456");
        requestDTO.setSenha("senhaForte123");

        responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Usuario Teste");
        responseDTO.setEmail("teste@dominio.com");
        responseDTO.setMatricula("123456");
    }

    // --- Teste 1: GET /api/usuarios (Listar todos) ---

    @Test
    @DisplayName("Deve retornar lista de usuários e status 200 OK")
    void deveListarTodosUsuariosComSucesso() throws Exception {
        // Dado (Given)
        when(usuarioService.getAllUsers()).thenReturn(List.of(responseDTO));

        // Quando & Então (When & Then)
        mockMvc.perform(get("/api/usuarios") 
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$[0].nome").value("Usuario Teste")) 
                .andExpect(jsonPath("$[0].matricula").value("123456"));
    }

    // --- Teste 2: POST /api/usuarios (Criar usuário com sucesso) ---

    @Test
    @DisplayName("Deve criar um novo usuário e retornar status 201 Created")
    void deveCriarNovoUsuarioComSucesso() throws Exception {
        // Dado (Given)
        when(usuarioService.criarNovoUsuario(any(UsuarioRequestDTO.class)))
                .thenReturn(responseDTO); // 

        String requestJson = objectMapper.writeValueAsString(requestDTO);

        // Quando & Então (When & Then)
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)) 
                .andExpect(status().isCreated()) 
                .andExpect(jsonPath("$.id").value(1L)) 
                .andExpect(jsonPath("$.nome").value("Usuario Teste"));
    }

    // --- Teste 3: POST /api/usuarios (Falha na validação) ---

    @Test
    @DisplayName("Não deve criar usuário com nome em branco e deve retornar status 400 Bad Request")
    void naoDeveCriarUsuarioComDadosInvalidos() throws Exception {
        // Dado (Given)
        requestDTO.setNome(""); 

        String requestJson = objectMapper.writeValueAsString(requestDTO);

        // Quando & Então (When & Then)
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest()); 

        verify(usuarioService, never()).criarNovoUsuario(any(UsuarioRequestDTO.class));
    }
}