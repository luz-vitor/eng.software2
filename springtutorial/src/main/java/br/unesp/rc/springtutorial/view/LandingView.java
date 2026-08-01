package br.unesp.rc.springtutorial.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import br.unesp.rc.springtutorial.view.fisica.FisicaView;

@Route("")
public class LandingView extends VerticalLayout
        implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.forwardTo(FisicaView.class);
    }

}