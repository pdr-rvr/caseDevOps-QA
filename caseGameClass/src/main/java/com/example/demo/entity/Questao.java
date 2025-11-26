package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_questao")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "enunciado") 
@ToString(exclude = "avaliacao") 
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String enunciado;

    @Column(nullable = false)
    private Integer pontos;

    // Relacionamento: Muitas Questões pertencem a uma Avaliação
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_id", nullable = false)
    private Avaliacao avaliacao;

    // Mapeamento de Value Objects (Opcao)
    @ElementCollection
    @CollectionTable(name = "tb_questao_opcoes", joinColumns = @JoinColumn(name = "questao_id"))
    private List<Opcao> opcoes = new ArrayList<>();

    // --- CONSTRUTOR DE DOMÍNIO ---
    public Questao(String enunciado, Integer pontos, List<Opcao> opcoes) {
        validarRegrasDeNegocio(enunciado, pontos, opcoes);
        
        this.enunciado = enunciado;
        this.pontos = pontos;
        this.opcoes = opcoes;
    }

    // --- Setter para vínculo bidirecional ---
    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    // --- VALIDAÇÕES ---
    private void validarRegrasDeNegocio(String enunciado, Integer pontos, List<Opcao> opcoes) {
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("O enunciado da questão é obrigatório.");
        }
        if (pontos == null || pontos <= 0) {
            throw new IllegalArgumentException("A pontuação deve ser maior que zero.");
        }
        if (opcoes == null || opcoes.size() != 5) {
            throw new IllegalArgumentException("Uma questão deve conter exatamente 5 opções.");
        }

        long corretas = opcoes.stream().filter(Opcao::isCorreta).count();
        if (corretas != 1) {
            throw new IllegalArgumentException("A questão deve ter exatamente 1 opção correta e 4 erradas.");
        }
    }
}