package br.unesp.rc.springtutorial.view.juridica;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import br.unesp.rc.springtutorial.entity.Juridica;
import br.unesp.rc.springtutorial.service.JuridicaService;
import br.unesp.rc.springtutorial.view.MainLayout;

@Route(value = "juridicas", layout = MainLayout.class)
@PageTitle("Pessoas Jurídicas")
public class JuridicaView extends VerticalLayout {

        private final JuridicaService service;

        private final JuridicaGrid grid;

        private final JuridicaEditor editor;

        private final TextField filtro = new TextField();

        private final Button novo = new Button("Nova Empresa");

        public JuridicaView(JuridicaService service,
                        JuridicaEditor editor) {

                this.service = service;

                this.editor = editor;

                this.grid = new JuridicaGrid();

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

                filtro.setPlaceholder("Pesquisar CNPJ");

                filtro.setValueChangeMode(
                                ValueChangeMode.LAZY);

        }

        private void configurarEventos() {

                filtro.addValueChangeListener(e -> atualizarGrid(e.getValue()));

                novo.addClickListener(e -> editor.editar(new Juridica()));

                grid.asSingleSelect()
                                .addValueChangeListener(e -> editor.editar(e.getValue()));

                editor.setChangeHandler(() -> {

                        editor.editar(null);

                        atualizarGrid(filtro.getValue());

                });

        }

        private void atualizarGrid(String cnpj) {

                if (StringUtils.hasText(cnpj)) {

                        grid.setItems(
                                        service.findByCnpj(cnpj));

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