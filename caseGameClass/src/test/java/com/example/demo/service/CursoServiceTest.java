package com.example.demo.service;

import com.example.demo.dto.CursoRequestDTO;
import com.example.demo.dto.CursoResponseDTO;
import com.example.demo.dto.ConteudoDTO;
import com.example.demo.entity.Curso;
import com.example.demo.entity.Email;
import com.example.demo.entity.Matricula;
import com.example.demo.entity.Senha;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso curso;
    private Usuario usuario;
    private CursoRequestDTO cursoRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario(
                "Usuario de Teste",
                new Email("setup.teste@dominio.com"),
                new Matricula("123456"),
                new Senha("hash$valido")
        );

        curso = new Curso("Java Completo", "Curso de Java");

        ConteudoDTO conteudoDTO = new ConteudoDTO("Aula 1", "http://aula1.com");
        cursoRequestDTO = new CursoRequestDTO();
        cursoRequestDTO.setNome("Java Completo");
        cursoRequestDTO.setDescricao("Curso de Java");
        cursoRequestDTO.setConteudos(List.of(conteudoDTO));
    }

    @Test
    @DisplayName("Deve criar um curso com sucesso")
    void deveCriarCursoComSucesso() {
        // Dado (Given)
        when(cursoRepository.existsByNome(anyString())).thenReturn(false);
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        // Quando (When)
        CursoResponseDTO response = cursoService.criarCurso(cursoRequestDTO);

        // Então (Then)
        assertNotNull(response);
        assertEquals("Java Completo", response.getNome());
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    @DisplayName("Deve inscrever usuário em curso com sucesso")
    void deveInscreverUsuarioEmCurso() {
        // Dado
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        // Quando
        cursoService.inscreverUsuarioEmCurso(1L, 1L);

        // Então
        verify(usuarioRepository, times(1)).save(usuario);
        assertTrue(usuario.getCursosInscritos().contains(curso));
        assertTrue(curso.getAlunosInscritos().contains(usuario));
    }

    @Test
    @DisplayName("Deve cancelar inscrição de usuário com sucesso")
    void deveCancelarInscricao() {
        // Dado
        usuario.inscreverEmCurso(curso); // Inscreve primeiro
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        
        // Confirma que estava inscrito
        assertTrue(usuario.getCursosInscritos().contains(curso));

        // Quando
        cursoService.cancelarInscricao(1L, 1L);

        // Então
        verify(usuarioRepository, times(1)).save(usuario);
        assertFalse(usuario.getCursosInscritos().contains(curso));
        assertFalse(curso.getAlunosInscritos().contains(usuario));
    }
}