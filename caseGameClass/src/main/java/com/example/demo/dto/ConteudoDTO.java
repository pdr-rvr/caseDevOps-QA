package com.example.demo.dto;

import com.example.demo.entity.Conteudo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.URL;

public class ConteudoDTO {

    @NotBlank(message = "O nome do conteúdo não pode ser vazio")
    private String nome;

    @NotBlank(message = "A URL do conteúdo não pode ser vazia")
    @URL(message = "URL inválida")
    private String urlVideo;

    // Construtor vazio
    public ConteudoDTO() {}

    // Construtor para mapeamento
    public ConteudoDTO(String nome, String urlVideo) {
        this.nome = nome;
        this.urlVideo = urlVideo;
    }

    // Mapeamento de Entidade para DTO
    public ConteudoDTO(Conteudo entity) {
        this.nome = entity.getNome();
        this.urlVideo = entity.getUrlVideo();
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getUrlVideo() { return urlVideo; }
    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo; }
}