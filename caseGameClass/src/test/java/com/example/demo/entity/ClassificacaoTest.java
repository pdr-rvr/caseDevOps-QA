package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ClassificacaoTest {

    @Test
    @DisplayName("Deve somar pontos corretamente")
    void deveSomarPontos() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = new Temporada("T1", "D", LocalDate.now(), LocalDate.now().plusDays(1));
        
        Classificacao classificacao = new Classificacao(aluno, temporada);
        assertEquals(0, classificacao.getPontuacaoTotal());

        classificacao.adicionarPontos(100);
        assertEquals(100, classificacao.getPontuacaoTotal());

        classificacao.adicionarPontos(50);
        assertEquals(150, classificacao.getPontuacaoTotal());
    }
    
    @Test
    @DisplayName("Não deve somar pontos negativos ou nulos")
    void naoDeveSomarPontosInvalidos() {
        Usuario aluno = mock(Usuario.class);
        Temporada temporada = new Temporada("T1", "D", LocalDate.now(), LocalDate.now().plusDays(1));
        Classificacao classificacao = new Classificacao(aluno, temporada);

        classificacao.adicionarPontos(null);
        classificacao.adicionarPontos(-10);
        
        assertEquals(0, classificacao.getPontuacaoTotal());
    }
}