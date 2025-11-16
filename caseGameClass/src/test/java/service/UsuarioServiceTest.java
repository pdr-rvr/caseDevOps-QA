package service;

import dto.UsuarioRequestDTO;
import dto.UsuarioResponseDTO;
import entity.Email;
import entity.Matricula;
import entity.Senha;
import entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.UsuarioRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {


    @Mock 
    private UsuarioRepository usuarioRepository;

    @Mock 
    private PasswordEncoder passwordEncoder;


    @InjectMocks 
    private UsuarioService usuarioService;

    private UsuarioRequestDTO requestDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("Teste");
        requestDTO.setEmail("teste@dominio.com");
        requestDTO.setMatricula("123456");
        requestDTO.setSenha("senha123");

        usuario = new Usuario(
                "Teste",
                new Email("teste@dominio.com"),
                new Matricula("123456"),
                new Senha("hash_salvo")
        );
    }

    // --- Teste 1: Listar Usuários ---

    @Test
    @DisplayName("Deve retornar uma lista de UsuarioResponseDTO")
    void deveRetornarListaDeUsuarios() {
        // Dado (Given)
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // Quando (When)
        List<UsuarioResponseDTO> resposta = usuarioService.getAllUsers();

        // Então (Then)
        assertNotNull(resposta);
        assertEquals(1, resposta.size());
        assertEquals("Teste", resposta.get(0).getNome());
        assertEquals("123456", resposta.get(0).getMatricula());
        
        verify(usuarioRepository, times(1)).findAll();
    }

    // --- Teste 2: Criar Usuário (Caminho Feliz) ---

    @Test
    @DisplayName("Deve criar um novo usuário com sucesso")
    void deveCriarNovoUsuarioComSucesso() {
        // Dado (Given)
        when(usuarioRepository.existsByEmail(any(Email.class))).thenReturn(false);
        when(usuarioRepository.existsByMatricula(any(Matricula.class))).thenReturn(false);

        when(passwordEncoder.encode(requestDTO.getSenha())).thenReturn("hash_gerado");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Quando (When)
        UsuarioResponseDTO responseDTO = usuarioService.criarNovoUsuario(requestDTO);

        // Então (Then)
        assertNotNull(responseDTO);
        assertEquals(requestDTO.getNome(), responseDTO.getNome());
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());

        verify(usuarioRepository, times(1)).existsByEmail(any(Email.class));
        verify(usuarioRepository, times(1)).existsByMatricula(any(Matricula.class));
        verify(passwordEncoder, times(1)).encode("senha123");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // --- Teste 3: Criar Usuário (Falha: Email já existe) ---

    @Test
    @DisplayName("Não deve criar usuário se o Email já existir")
    void naoDeveCriarUsuarioComEmailDuplicado() {
        // Dado (Given)
        when(usuarioRepository.existsByEmail(any(Email.class))).thenReturn(true);

        // Quando & Então (When & Then)
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.criarNovoUsuario(requestDTO);
        });

        assertEquals("O email informado já está em uso.", excecao.getMessage());

        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    // --- Teste 4: Criar Usuário (Falha: Matrícula já existe) ---

    @Test
    @DisplayName("Não deve criar usuário se a Matrícula já existir")
    void naoDeveCriarUsuarioComMatriculaDuplicada() {
        // Dado (Given)
        when(usuarioRepository.existsByEmail(any(Email.class))).thenReturn(false);
        when(usuarioRepository.existsByMatricula(any(Matricula.class))).thenReturn(true);

        // Quando & Então (When & Then)
        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.criarNovoUsuario(requestDTO);
        });

        assertEquals("A matrícula (RA) informada já está em uso.", excecao.getMessage());

        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
}