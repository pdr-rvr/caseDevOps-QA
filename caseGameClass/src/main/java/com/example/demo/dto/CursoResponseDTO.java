package com.example.demo.dto;

import com.example.demo.entity.Curso;
import java.util.List;
import java.util.stream.Collectors;

public class CursoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private List<ConteudoDTO> conteudos;

    // Construtor vazio
    public CursoResponseDTO() {}

    // Construtor de mapeamento (Entidade -> DTO)
    public CursoResponseDTO(Curso entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
        this.conteudos = entity.getConteudos().stream()
                .map(ConteudoDTO::new) // Converte cada Conteudo em ConteudoDTO
                .collect(Collectors.toList());
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<ConteudoDTO> getConteudos() { return conteudos; }
    public void setConteudos(List<ConteudoDTO> conteudos) { this.conteudos = conteudos; }
}