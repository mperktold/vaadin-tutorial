package it.prodata.views.events;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Events")
@Route("events")
@Menu(order = 0, icon = LineAwesomeIconUrl.BELL)
public class EventsView extends VerticalLayout {

    private static final String[] COLORS = new String[] {
        "#00F",
        "#0F0",
        "#0FF",
        "#F00",
        "#F0F",
        "#FF0",
    };

    private Registration btnClickListenerRegistration;
    private Registration divDomListenersRegistration;
    private Registration shortcutListenerRegistration;
    private Registration mouseOverRegistration;

    private TextField tfName;
    private Button btnSayHello;
    private FlexLayout outerClickArea;
    private Div stopPropagationClickArea;
    private Div continuePropagationClickArea;
    private Div hoverArea;

    public EventsView() {
        tfName = new TextField("Your name");
        btnSayHello = new Button("Say hello");
        btnSayHello.addClickShortcut(Key.ENTER);

        stopPropagationClickArea = new Div("Stop propagation");
        stopPropagationClickArea.getStyle()
            .setPadding("10px")
            .setBackgroundColor("#FFF")
            .setBorder("1px solid black");
        continuePropagationClickArea = new Div("Continue propagation");
        continuePropagationClickArea.getStyle()
            .setPadding("10px")
            .setBackgroundColor("#FFF")
            .setBorder("1px solid black");
        outerClickArea = new FlexLayout(stopPropagationClickArea, continuePropagationClickArea);
        outerClickArea.setFlexDirection(FlexLayout.FlexDirection.ROW);
        outerClickArea.getStyle()
            .set("gap", "10px")
            .setBackgroundColor("#FFF")
            .setPadding("10px")
            .setBorder("1px solid black");

        hoverArea = new Div("Hover");
        hoverArea.getStyle()
            .setPadding("10px")
            .setBorder("1px solid black");

        setMargin(true);
        HorizontalLayout horizontalLayout = new HorizontalLayout(tfName, btnSayHello);
        horizontalLayout.setAlignItems(Alignment.BASELINE);
        add(horizontalLayout, outerClickArea, hoverArea);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        registerButtonClickListener();
        registerDivDomListeners();
        registerShortcutListener();
        registerMouseOverListener();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        btnClickListenerRegistration.remove();
        divDomListenersRegistration.remove();
        shortcutListenerRegistration.remove();
        mouseOverRegistration.remove();
    }

    private static String randomColor() {
        return COLORS[(int)(Math.random() * COLORS.length)];
    }

    private void registerButtonClickListener() {
        btnClickListenerRegistration = btnSayHello.addClickListener(e -> {
            if (e.isCtrlKey())
                Notification.show("Good bye " + tfName.getValue());
            else
                Notification.show("Hello " + tfName.getValue());
        });
    }

    private void registerShortcutListener() {
        shortcutListenerRegistration = Shortcuts.addShortcutListener(this,
            () -> outerClickArea.getStyle().setBackgroundColor(randomColor()),
            Key.KEY_B,
            KeyModifier.CONTROL
        ).listenOn(tfName);
    }

    private void registerDivDomListeners() {
        divDomListenersRegistration = Registration.combine(
            outerClickArea.getElement()
                .addEventListener(
                    "click",
                    e -> outerClickArea.getStyle().setBackgroundColor(e.getEventData().getBoolean("event.ctrlKey")
						? "#FFF"
						: randomColor()
                    )
                )
                .addEventData("event.ctrlKey"),
            stopPropagationClickArea.getElement()
                .addEventListener("click",  e -> stopPropagationClickArea.getStyle().setBackgroundColor(e.getEventData().getBoolean("event.ctrlKey")
                    ? "#FFF"
                    : randomColor()
                ))
                .addEventData("event.ctrlKey")
                .stopPropagation(),
            continuePropagationClickArea.getElement()
                .addEventListener("click",  e -> continuePropagationClickArea.getStyle().setBackgroundColor(e.getEventData().getBoolean("event.ctrlKey")
                    ? "#FFF"
                    : randomColor()
                ))
                .addEventData("event.ctrlKey")
                .setFilter("!event.shiftKey")
        );
    }

    private void registerMouseOverListener() {
        mouseOverRegistration = ComponentUtil.addListener(hoverArea, MouseOverEvent.class, e -> {
           int color = (e.altKey ? 1 : 0)
               + (e.shiftKey ? 2 : 0)
               + (e.ctrlKey ? 4 : 0);
           hoverArea.getStyle().setBackgroundColor(color >= COLORS.length ? null : COLORS[color]);
        });
    }

    @DomEvent("mouseover")
    public static class MouseOverEvent extends ComponentEvent<Component> {

        public final boolean altKey;
        public final boolean ctrlKey;
        public final boolean shiftKey;

        public MouseOverEvent(
            Component source, boolean fromClient,
            @EventData("event.altKey") boolean altKey,
            @EventData("event.ctrlKey") boolean ctrlKey,
            @EventData("event.shiftKey") boolean shiftKey)
        {
            super(source, fromClient);
            this.altKey = altKey;
            this.ctrlKey = ctrlKey;
            this.shiftKey = shiftKey;
        }
    }
}
