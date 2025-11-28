package com.example.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nome da fila que vamos criar
    public static final String QUEUE_INSCRICOES = "fila-inscricoes-curso";

    @Bean
    public Queue inscricoesQueue() {
        // true = fila durável (não se perde se o RabbitMQ reiniciar)
        return new Queue(QUEUE_INSCRICOES, true);
    }
}
