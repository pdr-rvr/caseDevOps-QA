package com.example.demo.repository;

import com.example.demo.entity.Resultado;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    /**
     * Lista todo o histórico de resultados de um aluno específico.
     */
    List<Resultado> findByAluno(Usuario aluno);
    
    /**
     * Lista os resultados de um aluno em uma temporada específica.
     */
    List<Resultado> findByAlunoIdAndTemporadaId(Long alunoId, Long temporadaId);
}