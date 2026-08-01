package br.unesp.rc.springtutorial.view.fisica;

import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.grid.Grid;

import br.unesp.rc.springtutorial.entity.Fisica;

public class FisicaGrid extends Grid<Fisica> {

        public FisicaGrid() {

                addColumn(Fisica::getNome)
                                .setHeader("Nome")
                                .setAutoWidth(true);

                addColumn(Fisica::getCpf)
                                .setHeader("CPF")
                                .setAutoWidth(true);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                addColumn(f -> f.getDataNascimento() == null ? "" : f.getDataNascimento().format(formatter))
                                .setHeader("Nascimento")
                                .setAutoWidth(true);

                addColumn(f -> f.getContato() == null ? "" : f.getContato().getTelefoneResidencial())
                                .setHeader("Telefone")
                                .setAutoWidth(true);

                addColumn(f -> f.getContato() == null ? "" : f.getContato().getEmail())
                                .setHeader("Email")
                                .setAutoWidth(true);

                setSizeFull();
        }

}