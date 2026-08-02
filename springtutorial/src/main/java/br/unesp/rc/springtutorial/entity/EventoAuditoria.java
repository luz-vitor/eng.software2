package br.unesp.rc.springtutorial.entity;

import java.io.Serializable;
import java.time.Instant;

import br.unesp.rc.springtutorial.messaging.PessoaEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EventoAuditoria")
@Getter
@Setter
@ToString
public class EventoAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @Enumerated(EnumType.STRING)
    private PessoaEvent.TipoPessoa tipo;

    @Enumerated(EnumType.STRING)
    private PessoaEvent.Acao acao;

    private String documento;

    private String nome;

    private Instant ocorridoEm;

    public EventoAuditoria() {
    }

    public static EventoAuditoria de(PessoaEvent evento) {
        EventoAuditoria registro = new EventoAuditoria();

        registro.setTipo(evento.tipo());
        registro.setAcao(evento.acao());
        registro.setDocumento(evento.documento());
        registro.setNome(evento.nome());
        registro.setOcorridoEm(evento.ocorridoEm());

        return registro;
    }
}
