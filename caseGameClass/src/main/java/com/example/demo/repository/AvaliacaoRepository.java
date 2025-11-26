package com.example.demo.repository;

import com.example.demo.entity.Avaliacao;
import com.example.demo.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByCurso(Curso curso);
    
    List<Avaliacao> findByCursoId(Long cursoId);
}