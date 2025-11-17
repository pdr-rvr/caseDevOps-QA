package com.example.demo.controller;

import com.example.demo.dto.CursoRequestDTO;
import com.example.demo.dto.CursoResponseDTO;
import com.example.demo.dto.ConteudoDTO;
import com.example.demo.service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Importações para desabilitar a segurança no teste
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import com.example.demo.config.SecurityConfig; // Importe sua classe SecurityConfig

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CursoController.class,
            // Exclui a segurança padrão, assim como fizemos no UsuarioControllerTest
            excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(SecurityConfig.class) // Importa sua configuração de segurança real (se necessário)
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    private CursoRequestDTO requestDTO;
    private CursoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        ConteudoDTO conteudoDTO = new ConteudoDTO("Aula 1", "http://aula1.com");
        
        requestDTO = new CursoRequestDTO();
        requestDTO.setNome("Curso de Spring");
        requestDTO.setDescricao("Aprenda Spring Boot");
        requestDTO.setConteudos(List.of(conteudoDTO));

        responseDTO = new CursoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNome("Curso de Spring");
        responseDTO.setDescricao("Aprenda Spring Boot");
        responseDTO.setConteudos(List.of(conteudoDTO));
    }

    @Test
    @DisplayName("Deve criar um curso e retornar 201 Created")
    void deveCriarCursoComSucesso() throws Exception {
        // Dado
        when(cursoService.criarCurso(any(CursoRequestDTO.class))).thenReturn(responseDTO);

        // Quando & Então
        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Curso de Spring"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve inscrever usuário e retornar 204 No Content")
    void deveInscreverUsuario() throws Exception {
        // Dado
        doNothing().when(cursoService).inscreverUsuarioEmCurso(1L, 1L);

        // Quando & Então
        mockMvc.perform(post("/api/cursos/1/inscrever/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve cancelar inscrição e retornar 204 No Content")
    void deveCancelarInscricao() throws Exception {
        // Dado
        doNothing().when(cursoService).cancelarInscricao(1L, 1L);

        // Quando & Então
        mockMvc.perform(delete("/api/cursos/1/cancelar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve listar todos os cursos e retornar 200 OK")
    void deveListarTodosCursos() throws Exception {
        // Dado
        when(cursoService.listarTodosCursos()).thenReturn(List.of(responseDTO));

        // Quando & Então
        mockMvc.perform(get("/api/cursos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Curso de Spring"));
    }

    @Test
    @DisplayName("Deve buscar curso por ID e retornar 200 OK")
    void deveBuscarCursoPorId() throws Exception {
        // Dado
        when(cursoService.buscarCursoPorId(1L)).thenReturn(responseDTO);

        // Quando & Então
        mockMvc.perform(get("/api/cursos/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Curso de Spring"));
    }
}