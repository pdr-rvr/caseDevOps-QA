package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CursoRequestDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    private String nome;

    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;

    @Valid // Valida os DTOs de Conteúdo dentro da lista
    @NotEmpty(message = "Curso deve ter pelo menos um conteúdo")
    private List<ConteudoDTO> conteudos;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<ConteudoDTO> getConteudos() { return conteudos; }
    public void setConteudos(List<ConteudoDTO> conteudos) { this.conteudos = conteudos; }
}