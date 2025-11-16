package com.example.demo.entity;

import java.util.Objects;

public class Email {

    private String enderecoEmail;

    protected Email() {}

    public Email(String enderecoEmail) {
        if (enderecoEmail == null || !enderecoEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.enderecoEmail = enderecoEmail;
    }

    public String getEnderecoEmail() {
        return enderecoEmail;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(enderecoEmail, email.enderecoEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enderecoEmail);
    }

    @Override
    public String toString() {
        return enderecoEmail;
    }
    
}
