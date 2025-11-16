package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable 
public class Email implements Serializable {

    private String enderecoEmail;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    protected Email() {
    }

    public Email(String enderecoEmail) {
        if (enderecoEmail == null || !EMAIL_PATTERN.matcher(enderecoEmail).matches()) {
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
