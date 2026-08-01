package br.unesp.rc.springtutorial.view.fisica;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;

import br.unesp.rc.springtutorial.entity.Contato;
import br.unesp.rc.springtutorial.entity.Fisica;
import br.unesp.rc.springtutorial.service.FisicaService;

@Component
@UIScope
public class FisicaEditor extends VerticalLayout {

        private final FisicaService service;

        private Fisica fisica;

        private final TextField nome = new TextField("Nome");
        private final TextField cpf = new TextField("CPF");
        private final DatePicker dataNascimento = new DatePicker("Data de nascimento");

        private final TextField telefone = new TextField("Telefone");

        private final EmailField email = new EmailField("Email");

        private final Button salvar = new Button("Salvar");

        private final Button excluir = new Button("Excluir");

        private final Button cancelar = new Button("Cancelar");

        private ChangeHandler changeHandler;

        public FisicaEditor(FisicaService service) {

                this.service = service;

                cpf.setAllowedCharPattern("[0-9]");
                cpf.setMaxLength(11);

                dataNascimento.setLocale(
                                Locale.forLanguageTag("pt-BR"));

                FormLayout form = new FormLayout();

                form.add(
                                nome,
                                cpf,
                                dataNascimento,
                                telefone,
                                email);

                HorizontalLayout botoes = new HorizontalLayout(
                                salvar,
                                excluir,
                                cancelar);

                add(form, botoes);

                salvar.addClickListener(e -> salvar());

                excluir.addClickListener(e -> excluir());

                cancelar.addClickListener(e -> editar(null));

                setVisible(false);
        }

        private void salvar() {

                fisica.setNome(nome.getValue());

                fisica.setCpf(cpf.getValue());

                fisica.setDataNascimento(
                                dataNascimento.getValue());

                Contato contato = fisica.getContato();

                if (contato == null) {
                        contato = new Contato();
                }

                contato.setTelefoneResidencial(
                                telefone.getValue());

                contato.setEmail(
                                email.getValue());

                fisica.setContato(contato);

                service.save(fisica);

                Notification.show("Pessoa Física salva com sucesso.");

                editar(null);

                if (changeHandler != null) {
                        changeHandler.onChange();
                }

        }

        private void excluir() {

                if (fisica != null) {

                        service.delete(fisica);

                        Notification.show("Pessoa removida.");

                        editar(null);

                        if (changeHandler != null) {
                                changeHandler.onChange();
                        }

                }

        }

        public void editar(Fisica fisica) {

                if (fisica == null) {

                        setVisible(false);

                        this.fisica = null;

                        return;
                }

                this.fisica = fisica;

                nome.setValue(
                                fisica.getNome() == null ? "" : fisica.getNome());

                cpf.setValue(
                                fisica.getCpf() == null ? "" : fisica.getCpf());

                if (fisica.getDataNascimento() != null) {

                        dataNascimento.setValue(
                                        fisica.getDataNascimento());

                } else {

                        dataNascimento.clear();

                }

                if (fisica.getContato() != null) {

                        telefone.setValue(
                                        fisica.getContato()
                                                        .getTelefoneResidencial() == null ? ""
                                                                        : fisica.getContato()
                                                                                        .getTelefoneResidencial());

                        email.setValue(
                                        fisica.getContato()
                                                        .getEmail() == null ? ""
                                                                        : fisica.getContato()
                                                                                        .getEmail());

                } else {

                        telefone.clear();

                        email.clear();

                }

                setVisible(true);

                nome.focus();

        }

        public void setChangeHandler(ChangeHandler handler) {
                this.changeHandler = handler;
        }

        public interface ChangeHandler {
                void onChange();
        }

}