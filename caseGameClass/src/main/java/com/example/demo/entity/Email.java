package com.example.demo.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@EqualsAndHashCode 
@ToString 
public class Email {

    private String enderecoEmail;

    public Email(String enderecoEmail) {
        if (enderecoEmail == null || !enderecoEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.enderecoEmail = enderecoEmail;
    }
}