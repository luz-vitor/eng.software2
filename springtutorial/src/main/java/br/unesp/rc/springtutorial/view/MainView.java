package br.unesp.rc.springtutorial.view;

import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.repository.FisicaRepository;


@Route("view")
public class MainView extends VerticalLayout {

    private final FisicaRepository repo;
    private final Grid<Fisica> grid;
    private final FisicaEditor editor; 
    
    private final TextField filter = new TextField();
    private final Button novo = new Button("Novo");


    public MainView(FisicaRepository repo, FisicaEditor editor){
        this.repo = repo;
        this.editor = editor;
        
        setSizeFull();;
       
        grid = new Grid<>(Fisica.class, false);

        grid.addColumn(Fisica::getNome)
            .setHeader("Nome")
            .setAutoWidth(true);

        grid.addColumn(Fisica::getCpf)
            .setHeader("CPF")
            .setAutoWidth(true);

        grid.setSizeFull();

        filter.setPlaceholder("Filtrar por CPF");
        filter.setValueChangeMode(ValueChangeMode.LAZY);

        filter.addValueChangeListener(e -> 
            listFisica(e.getValue()));
        
        novo.addClickListener(e ->
            editor.editar(new Fisica()));

        grid.asSingleSelect()
            .addValueChangeListener(e -> 
                editor.editar(e.getValue()));

        editor.setChangeHandler(() -> {
            editor.editar(null);
            listFisica(filter.getValue());
        });

        HorizontalLayout actions =
                new HorizontalLayout(filter, novo);

        HorizontalLayout content =
                new HorizontalLayout(grid, editor);

        content.setSizeFull();
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, editor);

        add(actions, content);

        expand(content);

        listFisica("");
    }

    private void listFisica(String filterCPF){
        if(StringUtils.hasText(filterCPF)){
            grid.setItems(repo.findByCpf(filterCPF));
        }else{
            grid.setItems(repo.findAll());
        }

    }
}
    // private final FisicaService service;

    // private final Grid<Fisica> grid = new Grid<>(Fisica.class);

    // private final FisicaEditor editor;

    // public MainView(FisicaService service, FisicaEditor editor){

    //     this.service = service;
    //     this.editor = editor;

    //     Button novo = new Button("Novo");

    //     novo.addClickListener(e -> {editor.editar(new Fisica());

    //     });


    //     grid.setColumns(
    //         "nome",
    //         "cpf"
    //     );


    //     grid.asSingleSelect()
    //         .addValueChangeListener(event -> {
    //             editor.editar(event.getValue());
    //         });


    //     editor.setChangeHandler(() -> {
    //         atualizarGrid();
    //         editor.editar(null);
    //     });



    //     HorizontalLayout layout =
    //         new HorizontalLayout(
    //             grid,
    //             editor
    //         );


    //     add(
    //         novo,
    //         layout
    //     );


    //     atualizarGrid();
    // }

    // private void atualizarGrid(){
    //     grid.setItems(service.findAll());
    // }

// }