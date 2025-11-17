package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConteudoTest {

    @Test
    @DisplayName("Deve criar um conteúdo com dados válidos")
    void deveCriarConteudo() {
        // Dado
        String nome = "Aula de JPA";
        String url = "http://jpa.com";

        // Quando
        Conteudo conteudo = new Conteudo(nome, url);

        // Então
        assertNotNull(conteudo);
        assertEquals(nome, conteudo.getNome());
        assertEquals(url, conteudo.getUrlVideo());
        assertNull(conteudo.getCurso()); // Curso ainda não foi setado
    }
}