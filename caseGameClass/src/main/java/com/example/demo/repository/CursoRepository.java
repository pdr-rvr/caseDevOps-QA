package com.example.demo.repository;

import com.example.demo.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Optional<Curso> findByNome(String nome);
    boolean existsByNome(String nome);

    @Query("SELECT c FROM Curso c LEFT JOIN FETCH c.conteudos WHERE c.id = :id")
    Optional<Curso> findByIdWithConteudos(Long id);
}