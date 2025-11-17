package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConteudoTest {

    @Test
    @DisplayName("Deve criar um conteúdo e associar a um curso")
    void deveCriarConteudoEAssociarCurso() {

        String nome = "Aula de JPA";
        String url = "http://jpa.com";
        Curso curso = new Curso("Curso Teste", "Desc");

        Conteudo conteudo = new Conteudo(nome, url);
        conteudo.setCurso(curso);
        
        Conteudo conteudoVazio = new Conteudo();
        conteudoVazio.setUrlVideo("http://url.com");
        conteudoVazio.setNome("Nome");

        // Então
        assertNotNull(conteudo);
        assertEquals(nome, conteudo.getNome());
        assertEquals(url, conteudo.getUrlVideo());
        assertEquals(curso, conteudo.getCurso()); 
        
        assertNotNull(conteudoVazio);
    }

    @Test
    @DisplayName("Deve testar o retorno do ID")
    void deveTestarGetId() {
        Conteudo conteudo = new Conteudo();
        assertNull(conteudo.getId());
    }
}