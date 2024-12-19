package it.prodata.views.gridlayout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Grid Layout")
@Route("grid-layout")
@Menu(order = 0, icon = LineAwesomeIconUrl.BORDER_ALL_SOLID)
public class GridLayoutDemoView extends GridLayout {

    public GridLayoutDemoView() {
        setSizeFull();
        var header = new H3("Header");
        header.getStyle()
            .setPadding("10px")
            .setBorderBottom("1px solid black");
        var sidebar = new Div("Sidebar");
        sidebar.getStyle()
            .setPadding("10px")
            .setBorderRight("1px solid black");
        var content = new Div("Content");
        content.getStyle().setPadding("10px");
        var footer = new Div("Footer");
        footer.getStyle()
            .setPadding("10px")
            .setBorderTop("1px solid black");
        setGridTemplateAreas(
            "header header",
            "sidebar content",
            "footer footer"
        );
        setGridTemplateRows("auto 1fr auto");
        setGridTemplateColumns("200px 1fr");
        setGridArea("header", header);
        setGridArea("sidebar", sidebar);
        setGridArea("content", content);
        setGridArea("footer", footer);
        add(header, sidebar, content, footer);
    }

}
