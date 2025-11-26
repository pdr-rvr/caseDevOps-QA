package com.example.demo.repository;

import com.example.demo.entity.Avaliacao;
import com.example.demo.entity.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {

    /**
     * Lista todas as questões de uma determinada avaliação.
     */
    List<Questao> findByAvaliacao(Avaliacao avaliacao);
}