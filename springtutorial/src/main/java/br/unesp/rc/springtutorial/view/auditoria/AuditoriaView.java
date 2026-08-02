package br.unesp.rc.springtutorial.view.auditoria;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import br.unesp.rc.springtutorial.entity.EventoAuditoria;
import br.unesp.rc.springtutorial.messaging.AuditoriaBroadcaster;
import br.unesp.rc.springtutorial.repository.EventoAuditoriaRepository;
import br.unesp.rc.springtutorial.view.MainLayout;

@Route(value = "auditoria", layout = MainLayout.class)
@PageTitle("Auditoria")
public class AuditoriaView extends VerticalLayout {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private final EventoAuditoriaRepository repository;

    private final Grid<EventoAuditoria> grid = new Grid<>(EventoAuditoria.class, false);

    private Registration inscricao;

    public AuditoriaView(EventoAuditoriaRepository repository) {

        this.repository = repository;

        setSizeFull();

        Span legenda = new Span("Registro de alterações do cadastro, atualizado em tempo real.");

        grid.addColumn(e -> FORMATO.format(e.getOcorridoEm()))
                .setHeader("Quando")
                .setAutoWidth(true);

        grid.addColumn(e -> e.getTipo().getRotulo())
                .setHeader("Tipo")
                .setAutoWidth(true);

        grid.addColumn(e -> e.getAcao().getRotulo())
                .setHeader("Ação")
                .setAutoWidth(true);

        grid.addColumn(EventoAuditoria::getDocumento)
                .setHeader("Documento")
                .setAutoWidth(true);

        grid.addColumn(EventoAuditoria::getNome)
                .setHeader("Nome")
                .setAutoWidth(true);

        grid.setSizeFull();

        add(legenda, grid);

        expand(grid);

        atualizar();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {

        UI ui = attachEvent.getUI();

        inscricao = AuditoriaBroadcaster.registrar(evento -> ui.access(() -> {

            atualizar();

            Notification notificacao = Notification.show(
                    descrever(evento),
                    3000,
                    Notification.Position.BOTTOM_END);

            notificacao.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }));
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {

        if (inscricao != null) {
            inscricao.remove();
            inscricao = null;
        }
    }

    private String descrever(EventoAuditoria evento) {
        return "Pessoa "
                + evento.getTipo().getRotulo()
                + " "
                + evento.getAcao().getRotulo()
                + ": "
                + evento.getNome();
    }

    private void atualizar() {
        grid.setItems(repository.findAllByOrderByOcorridoEmDesc());
    }
}
