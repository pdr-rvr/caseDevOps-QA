package com.example.demo.entity;

import java.util.Objects;

public class Matricula {
    private String ra;

    protected Matricula() {}

    public Matricula(String ra) {
        if (ra == null || !ra.matches("^\\d{6}$")) {
            throw new IllegalArgumentException("RA inválido. Deve conter exatamente 6 números.");
        }
        this.ra = ra;
    }

    public String getRa() {
        return ra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return Objects.equals(ra, matricula.ra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ra);
    }

    @Override
    public String toString() {
        return ra; 
    }
}
