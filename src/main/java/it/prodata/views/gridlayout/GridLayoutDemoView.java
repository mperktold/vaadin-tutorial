package it.prodata.views.gridlayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Grid Layout")
@Route("grid-layout")
@Menu(order = 0, icon = LineAwesomeIconUrl.BORDER_ALL_SOLID)
public class GridLayoutDemoView extends AppLayout {

    public GridLayoutDemoView() {
        setSizeFull();
        setTitle("Title");
        Button btnHome = new Button("Home", VaadinIcon.HOME.create(), e -> setContent(new H4("Home")));
        btnHome.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        Button btnAbout = new Button("About", VaadinIcon.INFO_CIRCLE.create(), e -> setContent(new H4("About")));
        btnAbout.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        addToSideBar(btnHome, btnAbout);
        setContent(new H4("Home"));
        var footer = new Div("Footer");
        setFooter(footer);
    }
}
