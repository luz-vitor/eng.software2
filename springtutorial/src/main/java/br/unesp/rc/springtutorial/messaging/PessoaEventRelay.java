package br.unesp.rc.springtutorial.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PessoaEventRelay {

    private static final Logger log = LoggerFactory.getLogger(PessoaEventRelay.class);

    private final RabbitTemplate rabbitTemplate;

    public PessoaEventRelay(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publicarAposCommit(PessoaEvent evento) {

        String routingKey = evento.routingKey();

        log.info("Publicando evento no broker -> exchange={} routingKey={}",
                RabbitConfig.EXCHANGE, routingKey);

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                routingKey,
                evento);
    }
}
