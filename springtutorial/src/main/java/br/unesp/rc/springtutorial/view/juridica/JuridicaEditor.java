package br.unesp.rc.springtutorial.view.juridica;

import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;

import br.unesp.rc.springtutorial.entity.Contato;
import br.unesp.rc.springtutorial.entity.Juridica;
import br.unesp.rc.springtutorial.service.JuridicaService;

@Component
@UIScope
public class JuridicaEditor extends VerticalLayout {

        private final JuridicaService service;

        private Juridica juridica;

        private final TextField nome = new TextField("Razão Social");

        private final TextField cnpj = new TextField("CNPJ");

        private final TextField telefoneComercial = new TextField("Telefone Comercial");

        private final TextField celular = new TextField("Celular");

        private final EmailField email = new EmailField("Email");

        private final Button salvar = new Button("Salvar");

        private final Button excluir = new Button("Excluir");

        private final Button cancelar = new Button("Cancelar");

        private ChangeHandler changeHandler;

        public JuridicaEditor(JuridicaService service) {

                this.service = service;

                cnpj.setAllowedCharPattern("[0-9]");
                cnpj.setMaxLength(14);

                FormLayout form = new FormLayout();

                form.add(
                                nome,
                                cnpj,
                                telefoneComercial,
                                celular,
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

                juridica.setNome(nome.getValue());

                juridica.setCnpj(cnpj.getValue());

                Contato contato = juridica.getContato();

                if (contato == null) {
                        contato = new Contato();
                }

                contato.setTelefoneComercial(
                                telefoneComercial.getValue());

                contato.setCelular(
                                celular.getValue());

                contato.setEmail(
                                email.getValue());

                juridica.setContato(contato);

                service.save(juridica);

                Notification.show("Pessoa Jurídica salva.");

                editar(null);

                if (changeHandler != null) {
                        changeHandler.onChange();
                }

        }

        private void excluir() {

                if (juridica != null) {

                        service.delete(juridica);

                        Notification.show("Pessoa Jurídica removida.");

                        editar(null);

                        if (changeHandler != null) {
                                changeHandler.onChange();
                        }

                }

        }

        public void editar(Juridica juridica) {

                if (juridica == null) {

                        this.juridica = null;

                        setVisible(false);

                        return;

                }

                this.juridica = juridica;

                nome.setValue(
                                juridica.getNome() == null ? "" : juridica.getNome());

                cnpj.setValue(
                                juridica.getCnpj() == null ? "" : juridica.getCnpj());

                if (juridica.getContato() != null) {

                        telefoneComercial.setValue(
                                        juridica.getContato().getTelefoneComercial() == null ? ""
                                                        : juridica.getContato().getTelefoneComercial());

                        celular.setValue(
                                        juridica.getContato().getCelular() == null ? ""
                                                        : juridica.getContato().getCelular());

                        email.setValue(
                                        juridica.getContato().getEmail() == null ? ""
                                                        : juridica.getContato().getEmail());

                } else {

                        telefoneComercial.clear();
                        celular.clear();
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