package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CaseGameClassApplicationTest {
    
    @MockBean
    private ConnectionFactory connectionFactory;

    @Test
    @DisplayName("Deve carregar o contexto da aplicação")
    void contextLoads() {
    }

}
