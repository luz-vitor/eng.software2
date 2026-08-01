package br.unesp.rc.springtutorial.view.juridica;

import com.vaadin.flow.component.grid.Grid;

import br.unesp.rc.springtutorial.entity.Juridica;

public class JuridicaGrid extends Grid<Juridica> {

        public JuridicaGrid() {

                addColumn(Juridica::getNome)
                                .setHeader("Nome")
                                .setAutoWidth(true);

                addColumn(Juridica::getCnpj)
                                .setHeader("CNPJ")
                                .setAutoWidth(true);

                addColumn(j -> j.getContato() == null ? "" : j.getContato().getTelefoneComercial())
                                .setHeader("Telefone Comercial")
                                .setAutoWidth(true);

                addColumn(j -> j.getContato() == null ? "" : j.getContato().getCelular())
                                .setHeader("Celular")
                                .setAutoWidth(true);

                addColumn(j -> j.getContato() == null ? "" : j.getContato().getEmail())
                                .setHeader("Email")
                                .setAutoWidth(true);

                setSizeFull();
        }

}