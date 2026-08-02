package br.unesp.rc.springtutorial.messaging;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import br.unesp.rc.springtutorial.entity.EventoAuditoria;

public class AuditoriaBroadcaster {

    private static final Executor executor = Executors.newSingleThreadExecutor();

    private static final List<Consumer<EventoAuditoria>> ouvintes = new LinkedList<>();

    private AuditoriaBroadcaster() {
    }

    public static synchronized Registration registrar(Consumer<EventoAuditoria> ouvinte) {

        ouvintes.add(ouvinte);

        return () -> {
            synchronized (AuditoriaBroadcaster.class) {
                ouvintes.remove(ouvinte);
            }
        };
    }

    public static synchronized void publicar(EventoAuditoria evento) {

        for (Consumer<EventoAuditoria> ouvinte : ouvintes) {
            executor.execute(() -> ouvinte.accept(evento));
        }
    }
}
