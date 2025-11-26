package com.example.demo.repository;

import com.example.demo.entity.Avaliacao;
import com.example.demo.entity.Curso;
import com.example.demo.entity.Opcao;
import com.example.demo.entity.Questao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class AvaliacaoRepositoryTest {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private TestEntityManager entityManager;

    // Helper para criar lista de opções válidas (Entidade)
    private List<Opcao> criarOpcoesValidas() {
        List<Opcao> opcoes = new ArrayList<>();
        opcoes.add(new Opcao("Certa", true));
        opcoes.add(new Opcao("Errada 1", false));
        opcoes.add(new Opcao("Errada 2", false));
        opcoes.add(new Opcao("Errada 3", false));
        opcoes.add(new Opcao("Errada 4", false));
        return opcoes;
    }

    @Test
    @DisplayName("Deve salvar Avaliação completa com Questões e Opções (Cascade)")
    void deveSalvarAvaliacaoEmCascata() {
        // 1. Persiste o Curso
        Curso curso = new Curso("Curso Teste", "Desc");
        entityManager.persistAndFlush(curso);

        // 2. Cria Avaliação
        Avaliacao avaliacao = new Avaliacao("Prova Final", curso);

        // 3. Adiciona Questão (O construtor valida, então usamos dados válidos)
        Questao questao = new Questao("Quanto é 2+2?", 10, criarOpcoesValidas());
        avaliacao.adicionarQuestao(questao);

        // 4. Salva pelo repositório
        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        // 5. Validações
        assertNotNull(salva.getId());
        assertEquals(1, salva.getQuestoes().size());
        
        // Verifica se as opções foram salvas (deve ter 5)
        assertEquals(5, salva.getQuestoes().get(0).getOpcoes().size());
    }

    @Test
    @DisplayName("Deve encontrar avaliações por curso")
    void deveEncontrarPorCurso() {
        // Dado
        Curso curso = new Curso("Curso Java", "Desc");
        entityManager.persistAndFlush(curso);

        Avaliacao av1 = new Avaliacao("Prova 1", curso);
        Avaliacao av2 = new Avaliacao("Prova 2", curso);
        
        entityManager.persist(av1);
        entityManager.persist(av2);
        entityManager.flush();

        // Quando
        List<Avaliacao> encontradas = avaliacaoRepository.findByCurso(curso);

        // Então
        assertEquals(2, encontradas.size());
    }
}