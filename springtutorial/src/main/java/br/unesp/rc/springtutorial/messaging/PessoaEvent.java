package br.unesp.rc.springtutorial.messaging;

import java.time.Instant;

public record PessoaEvent(
        TipoPessoa tipo,
        Acao acao,
        String documento,
        String nome,
        Instant ocorridoEm) {

    public enum TipoPessoa {

        FISICA("física"),
        JURIDICA("jurídica");

        private final String rotulo;

        TipoPessoa(String rotulo) {
            this.rotulo = rotulo;
        }

        public String getRotulo() {
            return rotulo;
        }
    }

    public enum Acao {

        CRIADA("criada"),
        ATUALIZADA("atualizada"),
        REMOVIDA("removida");

        private final String rotulo;

        Acao(String rotulo) {
            this.rotulo = rotulo;
        }

        public String getRotulo() {
            return rotulo;
        }
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
