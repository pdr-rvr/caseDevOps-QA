package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OpcaoRequestDTO {

    @NotBlank(message = "O texto da opção é obrigatório")
    private String texto;

    @NotNull(message = "Deve informar se a opção é correta ou não")
    private Boolean correta;
}