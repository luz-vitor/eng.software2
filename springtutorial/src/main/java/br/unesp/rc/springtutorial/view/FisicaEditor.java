package br.unesp.rc.springtutorial.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.service.FisicaService;

@SpringComponent
@UIScope
public class FisicaEditor extends VerticalLayout {

    private final FisicaService service;

    private Fisica fisica;

    TextField nome = new TextField("Nome");
    TextField cpf = new TextField("CPF");

    Button salvar = new Button("Salvar");
    Button cancelar = new Button("Cancelar");
    Button excluir = new Button("Excluir");

    private ChangeHandler changeHandler;

    public FisicaEditor(FisicaService service) {

        this.service = service;
        cpf.setPattern("[0-9]*");
        cpf.setAllowedCharPattern("[0-9]");
        cpf.setPlaceholder("Somente números");

        add(
            nome,
            cpf,
            new HorizontalLayout(
                salvar,
                cancelar,
                excluir
            )
        );


        salvar.addClickListener(e -> salvar());
        excluir.addClickListener(e -> excluir());
        cancelar.addClickListener(e -> editar(null));

        setVisible(false);
    }


    private void salvar(){

        fisica.setNome(nome.getValue());
        fisica.setCpf(cpf.getValue());

        service.save(fisica);

        editar(null);

        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }


    private void excluir(){

        service.delete(fisica);

        editar(null);

        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }


    public void editar(Fisica f){

        if(f == null){
            setVisible(false);
            return;
        }

        this.fisica = f;

        nome.setValue(
            f.getNome() == null ? "" : f.getNome()
        );

        cpf.setValue(
            f.getCpf() == null ? "" : f.getCpf()
        );

        setVisible(true);
    }


    public void setChangeHandler(ChangeHandler handler){
        this.changeHandler = handler;
    }


    public interface ChangeHandler {
        void onChange();
    }
}