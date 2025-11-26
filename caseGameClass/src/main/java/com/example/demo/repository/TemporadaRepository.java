package com.example.demo.repository;

import com.example.demo.entity.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {

    /**
     * Busca a temporada que está marcada como ativa.
     * Retorna Optional porque pode não haver nenhuma temporada rodando no momento.
     */
    Optional<Temporada> findByAtivaTrue();

    /**
     * Verifica se já existe uma temporada com este nome (para evitar duplicatas no cadastro).
     */
    boolean existsByNome(String nome);
}