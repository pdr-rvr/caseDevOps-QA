package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_resultado")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temporada_id", nullable = false)
    private Temporada temporada;

    @Column(nullable = false)
    private Integer notaObtida;

    @Column(nullable = false)
    private LocalDateTime dataRealizacao;

    // --- CONSTRUTOR ---
    public Resultado(Usuario aluno, Avaliacao avaliacao, Temporada temporada, Integer notaObtida) {
        if (aluno == null || avaliacao == null || temporada == null) {
            throw new IllegalArgumentException("Aluno, Avaliação e Temporada são obrigatórios.");
        }
        if (notaObtida == null || notaObtida < 0) {
            throw new IllegalArgumentException("A nota não pode ser negativa.");
        }

        this.aluno = aluno;
        this.avaliacao = avaliacao;
        this.temporada = temporada;
        this.notaObtida = notaObtida;
        this.dataRealizacao = LocalDateTime.now(); // Registra o momento exato da criação
    }
}