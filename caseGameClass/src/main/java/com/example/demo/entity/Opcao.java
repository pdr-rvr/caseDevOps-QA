package com.example.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable 
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@AllArgsConstructor 
@EqualsAndHashCode
@ToString
public class Opcao {

    private String texto;
    private boolean correta;

}