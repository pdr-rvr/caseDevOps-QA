package com.example.demo.entity;

import java.util.Objects;

public class Senha {

    private String hash;

    protected Senha() {}
    
    public Senha(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Hash de senha não pode ser nulo ou vazio.");
        }
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Senha senha = (Senha) o;
        return Objects.equals(hash, senha.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }

    @Override
    public String toString() {
        return "Senha [hash=***]"; 
    }
}