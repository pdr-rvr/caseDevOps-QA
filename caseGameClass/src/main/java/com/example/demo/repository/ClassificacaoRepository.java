package com.example.demo.repository;

import com.example.demo.entity.Classificacao;
import com.example.demo.entity.Temporada;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    /**
     * Busca o registro de classificação de um aluno em uma temporada específica.
     * Usado para atualizar a pontuação (Upsert).
     */
    Optional<Classificacao> findByAlunoAndTemporada(Usuario aluno, Temporada temporada);

    /**
     * A QUERY DO PLACAR.
     * Busca todos os registros de uma temporada, ordenando por:
     * 1. Pontuação Total (Descendente - Maior para Menor)
     * 2. Última Atualização (Ascendente - Quem pontuou primeiro ganha no desempate)
     */
    List<Classificacao> findByTemporadaOrderByPontuacaoTotalDescUltimaAtualizacaoAsc(Temporada temporada);
}