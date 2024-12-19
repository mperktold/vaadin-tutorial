package it.prodata.views.npmcomponent;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Material Button")
@Route("material-web-button")
@Menu(order = 0, icon = LineAwesomeIconUrl.HAND_POINTER)
public class MaterialWebButtonView extends VerticalLayout {

    public MaterialWebButtonView() {
        MaterialWebButton btnDefault = new MaterialWebButton("Default");
        btnDefault.setType(MaterialWebButton.Type.DEFAULT);
        MaterialWebButton btnOutlined = new MaterialWebButton("Outlined");
        btnOutlined.setType(MaterialWebButton.Type.OUTLINED);
        MaterialWebButton btnRaised = new MaterialWebButton("Raised");
        btnRaised.setType(MaterialWebButton.Type.RAISED);
        MaterialWebButton btnUnelevated = new MaterialWebButton("Unelevated");
        btnUnelevated.setType(MaterialWebButton.Type.UNELEVATED);
        MaterialWebButton btnDense = new MaterialWebButton("Dense");
        btnDense.setType(MaterialWebButton.Type.RAISED);
        btnDense.setDense(true);
        add(btnDefault, btnOutlined, btnRaised, btnUnelevated, btnDense);
    }

}
