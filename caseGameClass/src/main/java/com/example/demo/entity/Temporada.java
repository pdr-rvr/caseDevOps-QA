package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "tb_temporada")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "nome") 
@ToString
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private boolean ativa;

    // --- CONSTRUTOR ---
    public Temporada(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        validarDatas(dataInicio, dataFim);
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da temporada é obrigatório.");
        }
        
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativa = true; // Por padrão, nasce ativa 
    }

    // --- MÉTODOS DE DOMÍNIO ---
    
    public void encerrarTemporada() {
        this.ativa = false;
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas de início e fim são obrigatórias.");
        }
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("A data final não pode ser anterior à data inicial.");
        }
    }
}