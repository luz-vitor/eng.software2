package br.unesp.rc.springtutorial.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.service.FisicaService;


@Route("view")
public class MainView extends VerticalLayout {


    private final FisicaService service;

    private final Grid<Fisica> grid =
            new Grid<>(Fisica.class);


    private final FisicaEditor editor;


    public MainView(
            FisicaService service,
            FisicaEditor editor
    ){

        this.service = service;
        this.editor = editor;


        Button novo = new Button("Novo");


        novo.addClickListener(e -> {
            editor.editar(new Fisica());
        });


        grid.setColumns(
            "nome",
            "cpf"
        );


        grid.asSingleSelect()
            .addValueChangeListener(event -> {
                editor.editar(event.getValue());
            });


        editor.setChangeHandler(() -> {
            atualizarGrid();
            editor.editar(null);
        });



        HorizontalLayout layout =
            new HorizontalLayout(
                grid,
                editor
            );


        add(
            novo,
            layout
        );


        atualizarGrid();
    }



    private void atualizarGrid(){

        grid.setItems(
            service.findAll()
        );
    }

}