package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_classificacao",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"usuario_id", "temporada_id"})
       })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"aluno", "temporada"}) // Identidade baseada no par Aluno+Temporada
@ToString
public class Classificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    @Column(nullable = false)
    private Integer pontuacaoTotal;

    @Column(nullable = false)
    private LocalDateTime ultimaAtualizacao;

    // --- CONSTRUTOR (Cria o registro inicial) ---
    public Classificacao(Usuario aluno, Temporada temporada) {
        if (aluno == null || temporada == null) {
            throw new IllegalArgumentException("Aluno e Temporada são obrigatórios.");
        }
        this.aluno = aluno;
        this.temporada = temporada;
        this.pontuacaoTotal = 0; // Começa com zero
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    // --- MÉTODOS DE DOMÍNIO ---

    /**
     * Adiciona pontos ao placar e atualiza a data para fins de desempate.
     */
    public void adicionarPontos(Integer pontos) {
        if (pontos != null && pontos > 0) {
            this.pontuacaoTotal += pontos;
            this.ultimaAtualizacao = LocalDateTime.now();
        }
    }
}