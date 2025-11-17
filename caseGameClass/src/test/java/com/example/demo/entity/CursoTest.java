package com.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursoTest {

    private Curso curso;
    private Conteudo conteudo1;
    private Conteudo conteudo2;

    @BeforeEach
    void setUp() {
        curso = new Curso("Curso de Teste", "Descrição do curso");
        conteudo1 = new Conteudo("Aula 1", "http://aula1.com");
        conteudo2 = new Conteudo("Aula 2", "http://aula2.com");
    }

    @Test
    @DisplayName("Deve adicionar um conteúdo ao curso com sucesso")
    void deveAdicionarConteudo() {
        // Dado
        assertTrue(curso.getConteudos().isEmpty());

        // Quando
        curso.addConteudo(conteudo1);

        // Então
        assertEquals(1, curso.getConteudos().size());
        assertTrue(curso.getConteudos().contains(conteudo1));
        // Testa o relacionamento bidirecional
        assertEquals(curso, conteudo1.getCurso());
    }

    @Test
    @DisplayName("Deve remover um conteúdo do curso com sucesso")
    void deveRemoverConteudo() {
        // Dado
        curso.addConteudo(conteudo1);
        curso.addConteudo(conteudo2);
        assertEquals(2, curso.getConteudos().size());

        // Quando
        curso.removeConteudo(conteudo1);

        // Então
        assertEquals(1, curso.getConteudos().size());
        assertFalse(curso.getConteudos().contains(conteudo1));
        assertTrue(curso.getConteudos().contains(conteudo2));
        // Testa se a referência foi removida
        assertNull(conteudo1.getCurso());
    }
}
