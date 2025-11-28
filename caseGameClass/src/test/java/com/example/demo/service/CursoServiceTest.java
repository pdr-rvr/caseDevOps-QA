package com.example.demo.service;

import com.example.demo.dto.ConteudoDTO;
import com.example.demo.dto.CursoRequestDTO;
import com.example.demo.dto.CursoResponseDTO;
import com.example.demo.entity.Curso;
import com.example.demo.entity.Email;
import com.example.demo.entity.Matricula;
import com.example.demo.entity.Senha;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;
    
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
                new Matricula("999999"),
                new Senha("hash$valido")
        );
        usuario.setId(1L);

        curso = new Curso("Java Completo", "Curso de Java");
        // Simula ID para testes de busca
        // (Usamos reflexão ou assumimos que o mock retorna o objeto configurado)
        
        ConteudoDTO conteudoDTO = new ConteudoDTO("Aula 1", "http://aula1.com");
        cursoRequestDTO = new CursoRequestDTO();
        cursoRequestDTO.setNome("Java Completo");
        cursoRequestDTO.setDescricao("Curso de Java");
        cursoRequestDTO.setConteudos(List.of(conteudoDTO));
    }

    // --- TESTES DE SUCESSO ---

    @Test
    @DisplayName("Deve listar todos os cursos")
    void deveListarTodosCursos() {
        when(cursoRepository.findAll()).thenReturn(List.of(curso));
        
        List<CursoResponseDTO> lista = cursoService.listarTodosCursos();
        
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Java Completo", lista.get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar curso por ID com sucesso")
    void deveBuscarCursoPorId() {
        when(cursoRepository.findByIdWithConteudos(1L)).thenReturn(Optional.of(curso));
        
        CursoResponseDTO dto = cursoService.buscarCursoPorId(1L);
        
        assertNotNull(dto);
        assertEquals("Java Completo", dto.getNome());
    }

    @Test
    @DisplayName("Deve criar um curso com sucesso")
    void deveCriarCursoComSucesso() {
        when(cursoRepository.existsByNome(anyString())).thenReturn(false);
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        CursoResponseDTO response = cursoService.criarCurso(cursoRequestDTO);

        assertNotNull(response);
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    @DisplayName("Deve inscrever usuário em curso com sucesso")
    void deveInscreverUsuarioEmCurso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        cursoService.inscreverUsuarioEmCurso(1L, 1L);

        verify(usuarioRepository, times(1)).save(usuario);
        assertTrue(usuario.getCursosInscritos().contains(curso));
    }

    @Test
    @DisplayName("Deve cancelar inscrição de usuário com sucesso")
    void deveCancelarInscricao() {
        usuario.inscreverEmCurso(curso);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        
        cursoService.cancelarInscricao(1L, 1L);

        verify(usuarioRepository, times(1)).save(usuario);
        assertFalse(usuario.getCursosInscritos().contains(curso));
    }

    // --- TESTES DE ERRO (Para cobrir Branches) ---

    @Test
    @DisplayName("Deve lançar erro ao tentar criar curso com nome duplicado")
    void naoDeveCriarCursoDuplicado() {
        when(cursoRepository.existsByNome(cursoRequestDTO.getNome())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            cursoService.criarCurso(cursoRequestDTO);
        });

        verify(cursoRepository, never()).save(any(Curso.class));
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar curso inexistente")
    void naoDeveBuscarCursoInexistente() {
        when(cursoRepository.findByIdWithConteudos(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.buscarCursoPorId(99L);
        });
    }

    @Test
    @DisplayName("Deve lançar erro ao inscrever usuário inexistente")
    void naoDeveInscreverUsuarioInexistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.inscreverUsuarioEmCurso(99L, 1L);
        });
    }

    @Test
    @DisplayName("Deve lançar erro ao inscrever em curso inexistente")
    void naoDeveInscreverEmCursoInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.inscreverUsuarioEmCurso(1L, 99L);
        });
    }

    @Test
    @DisplayName("Deve lançar erro ao cancelar inscrição de usuário inexistente")
    void naoDeveCancelarUsuarioInexistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.cancelarInscricao(99L, 1L);
        });
    }

    @Test
    @DisplayName("Deve lançar erro ao cancelar inscrição de curso inexistente")
    void naoDeveCancelarCursoInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.cancelarInscricao(1L, 99L);
        });
    }
}
