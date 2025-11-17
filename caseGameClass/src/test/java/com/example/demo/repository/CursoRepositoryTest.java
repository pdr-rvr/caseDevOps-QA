package com.example.demo.repository;

import com.example.demo.entity.Conteudo;
import com.example.demo.entity.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CursoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CursoRepository cursoRepository;

    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso("Spring Boot Avançado", "Curso sobre microserviços");
        Conteudo c1 = new Conteudo("O que é Docker", "http://docker.com");
        Conteudo c2 = new Conteudo("O que é Kubernetes", "http://k8s.io");
        
        // Adiciona usando o helper para manter a consistência
        curso.addConteudo(c1);
        curso.addConteudo(c2);
    }

    @Test
    @DisplayName("Deve salvar um curso e seus conteúdos (CascadeType.ALL)")
    void deveSalvarCursoComConteudos() {
        // Quando
        Curso cursoSalvo = cursoRepository.save(curso);

        // Então
        assertNotNull(cursoSalvo.getId());
        assertEquals(2, cursoSalvo.getConteudos().size());
        
        // Busca no banco para confirmar
        Curso cursoDoBanco = entityManager.find(Curso.class, cursoSalvo.getId());
        assertNotNull(cursoDoBanco);
        assertEquals("Spring Boot Avançado", cursoDoBanco.getNome());
        assertEquals(2, cursoDoBanco.getConteudos().size());
        assertEquals("O que é Docker", cursoDoBanco.getConteudos().get(0).getNome());
    }

    @Test
    @DisplayName("Deve encontrar um curso pelo nome")
    void deveEncontrarCursoPeloNome() {
        // Dado
        entityManager.persistAndFlush(curso);

        // Quando
        Optional<Curso> cursoEncontrado = cursoRepository.findByNome("Spring Boot Avançado");

        // Então
        assertTrue(cursoEncontrado.isPresent());
        assertEquals(curso.getId(), cursoEncontrado.get().getId());
    }

    @Test
    @DisplayName("Deve carregar um curso com seus conteúdos (Query findByIdWithConteudos)")
    void deveCarregarCursoComConteudos() {
         // Dado
         Curso cursoSalvo = entityManager.persistAndFlush(curso);
         entityManager.clear(); // Limpa o cache para forçar a query

         // Quando
         Optional<Curso> cursoComFetch = cursoRepository.findByIdWithConteudos(cursoSalvo.getId());

         // Então
         assertTrue(cursoComFetch.isPresent());
         // Confirma que a lista de conteúdos foi carregada (não está "lazy")
         assertNotNull(cursoComFetch.get().getConteudos());
         assertEquals(2, cursoComFetch.get().getConteudos().size());
    }
}