package br.unesp.rc.springtutorial.view.fisica;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.service.FisicaService;
import br.unesp.rc.springtutorial.view.MainLayout;

@Route(value = "fisicas", layout = MainLayout.class)
@PageTitle("Pessoas Físicas")
public class FisicaView extends VerticalLayout {

        private final FisicaService service;

        private final FisicaGrid grid;

        private final FisicaEditor editor;

        private final TextField filtro = new TextField();

        private final Button novo = new Button("Nova Pessoa");

        public FisicaView(FisicaService service,
                        FisicaEditor editor) {

                this.service = service;
                this.editor = editor;

                this.grid = new FisicaGrid();

                setSizeFull();

                configurarFiltro();

                configurarEventos();

                atualizarGrid(null);

                add(

                                criarBarra(),

                                criarConteudo()

                );

        }

        private void configurarFiltro() {

                filtro.setPlaceholder("Pesquisar CPF");

                filtro.setValueChangeMode(
                                ValueChangeMode.LAZY);

        }

        private void configurarEventos() {

                filtro.addValueChangeListener(e -> atualizarGrid(e.getValue()));

                novo.addClickListener(e -> editor.editar(new Fisica()));

                grid.asSingleSelect()
                                .addValueChangeListener(e -> editor.editar(e.getValue()));

                editor.setChangeHandler(() -> {

                        editor.editar(null);

                        atualizarGrid(filtro.getValue());

                });

        }

        private void atualizarGrid(String cpf) {

                if (StringUtils.hasText(cpf)) {

                        grid.setItems(
                                        service.findByCpf(cpf));

                } else {

                        grid.setItems(
                                        service.findAll());

                }
        }

        private HorizontalLayout criarBarra() {

                return new HorizontalLayout(

                                filtro,

                                novo

                );

        }

        private HorizontalLayout criarConteudo() {

                HorizontalLayout layout = new HorizontalLayout(

                                grid,

                                editor

                );

                layout.setSizeFull();

                layout.setFlexGrow(2, grid);

                layout.setFlexGrow(1, editor);

                return layout;

        }

}