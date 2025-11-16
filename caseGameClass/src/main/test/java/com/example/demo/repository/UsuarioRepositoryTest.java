package com.example.demo.repository;

import entity.Email;
import entity.Matricula;
import entity.Senha;
import entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException; // Importante

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest 
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager; 

    /**
     * Helper para criar um usuário de teste válido
     */
    private Usuario criarUsuarioDeTeste() {
        Email email = new Email("teste@dominio.com");
        Matricula matricula = new Matricula("123456");
        Senha senha = new Senha("hash$senha");
        return new Usuario("Usuario Teste", email, matricula, senha);
    }

    // --- 1. Teste de Mapeamento (CRUD Básico) ---

    @Test
    @DisplayName("Deve salvar e buscar um Usuário com sucesso")
    void deveSalvarEBuscarUsuario() {
        // Dado (Given)
        Usuario novoUsuario = criarUsuarioDeTeste();

        // Quando (When)
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // Então (Then)
        assertNotNull(usuarioSalvo.getId()); 

        Optional<Usuario> usuarioBuscadoOpt = usuarioRepository.findById(usuarioSalvo.getId());
        
        assertTrue(usuarioBuscadoOpt.isPresent());
        Usuario usuarioBuscado = usuarioBuscadoOpt.get();

        assertEquals(novoUsuario.getNome(), usuarioBuscado.getNome());
        assertEquals(novoUsuario.getEmail(), usuarioBuscado.getEmail());
        assertEquals(novoUsuario.getMatricula(), usuarioBuscado.getMatricula());
        assertEquals(novoUsuario.getSenha(), usuarioBuscado.getSenha());
    }

    // --- 2. Testes dos Métodos Customizados (Find) ---

    @Test
    @DisplayName("Deve encontrar um Usuário pela Matrícula")
    void deveEncontrarUsuarioPorMatricula() {
        // Dado
        Usuario usuario = criarUsuarioDeTeste();
        entityManager.persistAndFlush(usuario); 

        // Quando
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByMatricula(usuario.getMatricula());

        // Então
        assertTrue(usuarioBuscado.isPresent());
        assertEquals(usuario.getId(), usuarioBuscado.get().getId());
    }

    @Test
    @DisplayName("Deve encontrar um Usuário pelo Email")
    void deveEncontrarUsuarioPorEmail() {
        // Dado
        Usuario usuario = criarUsuarioDeTeste();
        entityManager.persistAndFlush(usuario);

        // Quando
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(usuario.getEmail());

        // Então
        assertTrue(usuarioBuscado.isPresent());
        assertEquals(usuario.getId(), usuarioBuscado.get().getId());
    }

    // --- 3. Testes dos Métodos Customizados (Exists) ---

    @Test
    @DisplayName("Deve retornar true se Matrícula existe")
    void deveRetornarTrueParaMatriculaExistente() {
        // Dado
        Usuario usuario = criarUsuarioDeTeste();
        entityManager.persistAndFlush(usuario);

        // Quando
        boolean existe = usuarioRepository.existsByMatricula(usuario.getMatricula());

        // Então
        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve retornar false se Matrícula não existe")
    void deveRetornarFalseParaMatriculaInexistente() {
        // Quando
        boolean existe = usuarioRepository.existsByMatricula(new Matricula("000000"));

        // Então
        assertFalse(existe);
    }

    @Test
    @DisplayName("Deve retornar true se Email existe")
    void deveRetornarTrueParaEmailExistente() {
        // Dado
        Usuario usuario = criarUsuarioDeTeste();
        entityManager.persistAndFlush(usuario);

        // Quando
        boolean existe = usuarioRepository.existsByEmail(usuario.getEmail());

        // Então
        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve retornar false se Email não existe")
    void deveRetornarFalseParaEmailInexistente() {
        // Quando
        boolean existe = usuarioRepository.existsByEmail(new Email("nao@existe.com"));

        // Então
        assertFalse(existe);
    }

    // --- 4. Testes das Constraints (unique=true) ---

    @Test
    @DisplayName("Não deve salvar Usuário com Matrícula duplicada")
    void naoDeveSalvarMatriculaDuplicada() {
        // Dado
        Usuario usuario1 = criarUsuarioDeTeste();
        entityManager.persistAndFlush(usuario1); 

        Usuario usuario2 = new Usuario("Outro Nome", new Email("outro@email.com"), usuario1.getMatricula(), new Senha("hash123"));

        // Quando & Então
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.saveAndFlush(usuario2); 
        });
    }

    @Test
    @DisplayName("Não deve salvar Usuário com Email duplicado")
    void naoDeveSalvarEmailDuplicado() {
        // Dado
        Usuario usuario1 = criarUsuarioDeTeste();
        entityManager.persistAndFlush(usuario1); 

        Usuario usuario2 = new Usuario("Outro Nome", usuario1.getEmail(), new Matricula("987654"), new Senha("hash123"));

        // Quando & Então
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.saveAndFlush(usuario2);
        });
    }
}