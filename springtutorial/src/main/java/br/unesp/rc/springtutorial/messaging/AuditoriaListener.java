package br.unesp.rc.springtutorial.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.unesp.rc.springtutorial.entity.EventoAuditoria;
import br.unesp.rc.springtutorial.repository.EventoAuditoriaRepository;

@Component
public class AuditoriaListener {

    private static final Logger log = LoggerFactory.getLogger(AuditoriaListener.class);

    private final EventoAuditoriaRepository repository;

    public AuditoriaListener(EventoAuditoriaRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_AUDITORIA)
    public void receber(PessoaEvent evento) {

        if ("FALHAR".equalsIgnoreCase(evento.nome())) {
            log.warn("Falha proposital para demonstrar a Dead Letter Queue");
            throw new IllegalStateException("Falha simulada no processamento da auditoria");
        }

        EventoAuditoria registro = repository.save(EventoAuditoria.de(evento));

        log.info("[AUDITORIA] tipo={} acao={} documento={} nome={}",
                registro.getTipo(),
                registro.getAcao(),
                registro.getDocumento(),
                registro.getNome());

        AuditoriaBroadcaster.publicar(registro);
    }
}
