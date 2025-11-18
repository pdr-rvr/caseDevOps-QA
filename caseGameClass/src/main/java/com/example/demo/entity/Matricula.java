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
public class Matricula {
    private String ra;

    public Matricula(String ra) {
        if (ra == null || !ra.matches("^\\d{6}$")) {
            throw new IllegalArgumentException("RA inválido. Deve conter exatamente 6 números.");
        }
        this.ra = ra;
    }
}