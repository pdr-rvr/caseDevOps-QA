package com.example.demo.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@EqualsAndHashCode 
public class Senha {

    private String hash;

    public Senha(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Hash de senha não pode ser nulo ou vazio.");
        }
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Senha [hash=***]";
    }
}