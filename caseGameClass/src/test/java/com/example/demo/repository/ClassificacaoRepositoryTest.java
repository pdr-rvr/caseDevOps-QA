package com.example.demo.repository;

import com.example.demo.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ClassificacaoRepositoryTest {

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve retornar placar ordenado por pontos (desc) e data (asc)")
    void deveRetornarPlacarOrdenado() {
        // 1. Setup Temporada e Alunos
        Temporada temp = new Temporada("Temp 1", "Desc", LocalDate.now(), LocalDate.now().plusDays(10));
        entityManager.persist(temp);

        Usuario u1 = criarUsuario("Joao", "111111");
        Usuario u2 = criarUsuario("Maria", "222222");
        Usuario u3 = criarUsuario("Pedro", "333333");

        // 2. Setup Classificações
        // Maria: 100 pts
        Classificacao cMaria = new Classificacao(u2, temp);
        cMaria.adicionarPontos(100);
        entityManager.persist(cMaria);

        // Pedro: 50 pts
        Classificacao cPedro = new Classificacao(u3, temp);
        cPedro.adicionarPontos(50);
        entityManager.persist(cPedro);

        // Joao: 200 pts (Campeão)
        Classificacao cJoao = new Classificacao(u1, temp);
        cJoao.adicionarPontos(200);
        entityManager.persist(cJoao);
        
        entityManager.flush();

        // 3. Executa Query
        List<Classificacao> ranking = classificacaoRepository.findByTemporadaOrderByPontuacaoTotalDescUltimaAtualizacaoAsc(temp);

        // 4. Assert Ordem
        assertEquals(3, ranking.size());
        assertEquals("Joao", ranking.get(0).getAluno().getNome()); // 200 pts
        assertEquals("Maria", ranking.get(1).getAluno().getNome()); // 100 pts
        assertEquals("Pedro", ranking.get(2).getAluno().getNome()); // 50 pts
    }

    private Usuario criarUsuario(String nome, String ra) {
        Usuario u = new Usuario(nome, new Email(nome+"@t.com"), new Matricula(ra), new Senha("123"));
        entityManager.persist(u);
        return u;
    }
}