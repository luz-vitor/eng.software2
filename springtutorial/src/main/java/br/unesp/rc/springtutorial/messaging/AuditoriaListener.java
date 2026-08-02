package br.unesp.rc.springtutorial.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaListener {

    private static final Logger log = LoggerFactory.getLogger(AuditoriaListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_AUDITORIA)
    public void receber(PessoaEvent evento) {

        if ("FALHAR".equalsIgnoreCase(evento.nome())) {
            log.warn("Falha proposital para demonstrar a Dead Letter Queue");
            throw new IllegalStateException("Falha simulada no processamento da auditoria");
        }

        log.info("[AUDITORIA] tipo={} acao={} documento={} nome={} em={}",
                evento.tipo(),
                evento.acao(),
                evento.documento(),
                evento.nome(),
                evento.ocorridoEm());
    }
}
