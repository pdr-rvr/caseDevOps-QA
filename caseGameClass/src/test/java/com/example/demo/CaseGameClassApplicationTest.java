package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CaseGameClassApplicationTest {

    @Test
    @DisplayName("Deve carregar o contexto da aplicação")
    void contextLoads() {
    }

    @Test
    @DisplayName("Deve executar o método main sem erros")
    void deveExecutarMetodoMain() {
        CaseGameClassApplication.main(new String[] {});
    }
}