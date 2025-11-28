package com.example.demo.service;

import com.example.demo.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_INSCRICOES)
    public void receberNotificacao(String mensagem) {
        System.out.println("=================================================");
        System.out.println(" [MICROSSERVIÇO DE NOTIFICAÇÃO] Recebido: " + mensagem);
        System.out.println(" >> Enviando e-mail de boas-vindas...");
        System.out.println("=================================================");
    }
}
