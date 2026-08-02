package br.unesp.rc.springtutorial.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.icon.VaadinIcon;

import br.unesp.rc.springtutorial.view.auditoria.AuditoriaView;
import br.unesp.rc.springtutorial.view.fisica.FisicaView;
import br.unesp.rc.springtutorial.view.juridica.JuridicaView;

public class MainLayout extends AppLayout {

        public MainLayout() {

                H2 titulo = new H2("Cadastro de Pessoas");

                SideNav nav = new SideNav();

                nav.addItem(new SideNavItem(
                                "Pessoa Física",
                                FisicaView.class,
                                VaadinIcon.USER.create()));

                nav.addItem(new SideNavItem(
                                "Pessoa Jurídica",
                                JuridicaView.class,
                                VaadinIcon.BUILDING.create()));

                nav.addItem(new SideNavItem(
                                "Auditoria",
                                AuditoriaView.class,
                                VaadinIcon.CLIPBOARD_TEXT.create()));

                VerticalLayout drawer = new VerticalLayout(
                                titulo,
                                nav);

                drawer.setPadding(true);
                drawer.setSpacing(true);
                drawer.setSizeFull();

                addToDrawer(drawer);
        }
}