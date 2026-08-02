package br.unesp.rc.springtutorial.messaging;

import java.time.Instant;

public record PessoaEvent(
        TipoPessoa tipo,
        Acao acao,
        String documento,
        String nome,
        Instant ocorridoEm) {

    public enum TipoPessoa {
        FISICA, JURIDICA
    }

    public enum Acao {
        CRIADA, ATUALIZADA, REMOVIDA
    }

    public static PessoaEvent de(TipoPessoa tipo, Acao acao, String documento, String nome) {
        return new PessoaEvent(tipo, acao, documento, nome, Instant.now());
    }

    public String routingKey() {
        return "pessoa."
                + tipo.name().toLowerCase()
                + "."
                + acao.name().toLowerCase();
    }
}
