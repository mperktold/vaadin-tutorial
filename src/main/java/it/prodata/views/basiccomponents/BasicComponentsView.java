package it.prodata.views.basiccomponents;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Basic Components")
@Route("basiccomponents")
@Menu(order = 0, icon = LineAwesomeIconUrl.HAMMER_SOLID)
public class BasicComponentsView extends VerticalLayout {

    public BasicComponentsView() {
        VerticalLayout headings = createHeadings();
        VerticalLayout inputs = createInputs();
        HorizontalLayout hlHtml = new HorizontalLayout(headings, inputs);
        hlHtml.setWidthFull();
        hlHtml.setFlexGrow(1, headings, inputs);
        VerticalLayout vaadinButtons = createVaadinButtons();
        VerticalLayout vaadinFields = createVaadinFields();
        HorizontalLayout hlVaadin = new HorizontalLayout(vaadinButtons, vaadinFields);
        hlVaadin.setWidthFull();
        hlVaadin.setFlexGrow(1, headings, inputs);
        add(
            hlHtml,
            hlVaadin,
			createLayouts()
        );
    }

    private static VerticalLayout createHeadings() {
		VerticalLayout headings = new VerticalLayout(
			new Span("Headings"),
			new H1("Heading 1"),
			new H2("Heading 2"),
			new H3("Heading 3"),
			new H4("Heading 4"),
			new H5("Heading 5")
		);
		headings.getStyle().setBorder("1px solid black");
		return headings;
    }

    private static VerticalLayout createInputs() {
		NativeLabel lblText = new NativeLabel("Text: ");
        Input inputText = new Input();
        inputText.setType("text");
		inputText.setId("text");
		lblText.setFor(inputText);
		NativeLabel lblCheckbox = new NativeLabel("Checkbox: ");
        Input inputCheckbox = new Input();
        inputCheckbox.setType("checkbox");
		inputCheckbox.setId("checkbox");
		lblCheckbox.setFor(inputCheckbox);
		NativeLabel lblFile = new NativeLabel("File: ");
        Input inputFile = new Input();
        inputFile.setType("file");
		inputFile.setId("file");
		lblFile.setFor(inputFile);
		NativeLabel lblDate = new NativeLabel("Date: ");
        Input inputDate = new Input();
        inputDate.setType("date");
		inputDate.setId("date");
		lblDate.setFor(inputDate);
		NativeLabel lblTime = new NativeLabel("Time: ");
        Input inputTime = new Input();
        inputTime.setType("time");
		inputTime.setId("time");
		lblTime.setFor(inputTime);
		NativeLabel lblRange = new NativeLabel("Range: ");
        Input inputRange = new Input();
        inputRange.setType("range");
		inputRange.setId("range");
		lblRange.setFor(inputRange);
		VerticalLayout inputs = new VerticalLayout(
			new Span("Inputs"),
			new Div(lblText, inputText),
			new Div(lblCheckbox, inputCheckbox),
			new Div(lblFile, inputFile),
			new Div(lblDate, inputDate),
			new Div(lblTime, inputTime),
			new Div(lblRange, inputRange)
		);
		inputs.getStyle().setBorder("1px solid black");
		return inputs;
    }

    private static VerticalLayout createVaadinButtons() {
        Button btnPrimary1 = new Button("Button");
        btnPrimary1.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button btnSecondary1 = new Button("Button");
        Button btnTertiary1 = new Button("Button");
        btnTertiary1.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        Button btnIcon1 = new Button(VaadinIcon.STAR.create());
        btnIcon1.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON);

        Button btnPrimary2 = new Button("Button");
        btnPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        Button btnSecondary2 = new Button("Button");
        btnSecondary2.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button btnTertiary2 = new Button("Button");
        btnTertiary2.addThemeVariants(ButtonVariant.LUMO_TERTIARY,ButtonVariant.LUMO_SUCCESS);
        Button btnIcon2 = new Button(VaadinIcon.STAR.create());
        btnIcon2.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON ,ButtonVariant.LUMO_SUCCESS);

        Button btnPrimary3 = new Button("Button");
        btnPrimary3.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_ERROR);
        Button btnSecondary3 = new Button("Button");
        btnSecondary3.addThemeVariants(ButtonVariant.LUMO_ERROR);
        Button btnTertiary3 = new Button("Button");
        btnTertiary3.addThemeVariants(ButtonVariant.LUMO_TERTIARY,ButtonVariant.LUMO_ERROR);
        Button btnIcon3 = new Button(VaadinIcon.STAR.create());
        btnIcon3.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON,ButtonVariant.LUMO_ERROR);

        Button btnPrimary4 = new Button("Button");
        btnPrimary4.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_WARNING);
        Button btnSecondary4 = new Button("Button");
        btnSecondary4.addThemeVariants(ButtonVariant.LUMO_WARNING);
        Button btnTertiary4 = new Button("Button");
        btnTertiary4.addThemeVariants(ButtonVariant.LUMO_TERTIARY,ButtonVariant.LUMO_WARNING);
        Button btnIcon4 = new Button(VaadinIcon.STAR.create());
        btnIcon4.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ICON,ButtonVariant.LUMO_WARNING);

		VerticalLayout buttons = new VerticalLayout(
			new Span("Vaadin button"),
			new HorizontalLayout(btnPrimary1, btnSecondary1, btnTertiary1, btnIcon1),
			new HorizontalLayout(btnPrimary2, btnSecondary2, btnTertiary2, btnIcon2),
			new HorizontalLayout(btnPrimary3, btnSecondary3, btnTertiary3, btnIcon3),
			new HorizontalLayout(btnPrimary4, btnSecondary4, btnTertiary4, btnIcon4)
		);
		buttons.getStyle().setBorder("1px solid black");
		return buttons;
    }

    private static VerticalLayout createVaadinFields() {
        TextField tf = new TextField("Text");
        PasswordField pwd = new PasswordField("Password");
        DatePicker dp = new DatePicker("Date");
        TimePicker tp = new TimePicker("Time");
        Select<String> sel = new Select<>();
        sel.setLabel("Select");
        sel.setItems("Item 1", "Item 2", "Item 3");
        ComboBox<String> cb = new ComboBox<>("Combobox");
        cb.setItems("Item 1", "Item 2", "Item 3");
        cb.setAllowCustomValue(true);
        Checkbox chk = new Checkbox("Checkbox");
        chk.setIndeterminate(true);

		VerticalLayout fields = new VerticalLayout(
			new Span("Vaadin fields"),
			new HorizontalLayout(tf, pwd),
			new HorizontalLayout(dp, tp),
			new HorizontalLayout(sel, cb),
			chk
		);
		fields.setSpacing(false);
		fields.getStyle().setBorder("1px solid black");
		return fields;
    }

    private static VerticalLayout createLayouts() {
        Tabs tabs = new Tabs(
            new Tab("Tab1"),
            new Tab("Tab2"),
            new Tab("Tab3")
        );
        Div content = new Div("Tab1");
        tabs.addSelectedChangeListener(e -> content.setText(e.getSelectedTab().getLabel()));
        VerticalLayout vl1 = new VerticalLayout(tabs, content);
        vl1.getStyle().setBorder("1px solid black");

        Details details = new Details("Summary");
        details.add("Details");
        Accordion accordion = new Accordion();
        accordion.add("Summary 1", new Span("Content 1"));
        accordion.add("Summary 2", new Span("Content 2"));
        VerticalLayout vl2 = new VerticalLayout(details, accordion);
        vl2.getStyle().setBorder("1px solid black");
        HorizontalLayout hl = new HorizontalLayout(vl1, vl2);
        hl.setWidthFull();
        hl.setSpacing(true);
		VerticalLayout layout = new VerticalLayout(
			new Span("Layout"),
			hl
		);
		layout.getStyle().setBorder("1px solid black");
		return layout;
    }
}
