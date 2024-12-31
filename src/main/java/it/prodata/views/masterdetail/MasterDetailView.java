package it.prodata.views.masterdetail;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import it.prodata.data.SamplePerson;
import it.prodata.services.SamplePersonService;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Master-Detail")
@Route("master-detail/:samplePersonID?/:action?(edit)")
@Menu(order = 3, icon = LineAwesomeIconUrl.COLUMNS_SOLID)
@Uses(Icon.class)
public class MasterDetailView extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "samplePersonID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    private final Grid<SamplePerson> grid = new Grid<>();

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField email = new TextField("Email");
    private final TextField phone = new TextField("Phone");
    private final DatePicker dateOfBirth = new DatePicker("Date Of Birth");
    private final TextField occupation = new TextField("Occupation");
    private final TextField role = new TextField("Role");
    private final Checkbox important = new Checkbox("Important");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

	private final Binder<SamplePerson> binder = new Binder<>();

    private SamplePerson samplePerson;

    private final SamplePersonService samplePersonService;

    public MasterDetailView(SamplePersonService samplePersonService) {
        this.samplePersonService = samplePersonService;
        addClassNames("master-detail-view");

        // Configure Grid
        grid.addColumn(SamplePerson::getFirstName).setHeader("Firstname").setAutoWidth(true);
        grid.addColumn(SamplePerson::getLastName).setHeader("Lastname").setAutoWidth(true);
        grid.addColumn(SamplePerson::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(SamplePerson::getPhone).setHeader("Phone").setAutoWidth(true);
        grid.addColumn(SamplePerson::getDateOfBirth).setHeader("Birthdate").setAutoWidth(true);
        grid.addColumn(SamplePerson::getOccupation).setHeader("Occupation").setAutoWidth(true);
        grid.addColumn(SamplePerson::getRole).setHeader("Role").setAutoWidth(true);

        LitRenderer<SamplePerson> importantRenderer = LitRenderer
            .<SamplePerson>of(
            "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>"
            )
            .withProperty("icon", person -> person.isImportant() ? "check" : "minus")
            .withProperty("color", person -> person.isImportant()
                ? "var(--lumo-primary-text-color)"
                : "var(--lumo-disabled-text-color)"
            );
        grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null)
				UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
			else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailView.class);
            }
        });

		binder.forField(firstName)
            .asRequired()
            .bind(SamplePerson::getFirstName, SamplePerson::setFirstName);
        binder.forField(lastName)
            .asRequired()
            .bind(SamplePerson::getLastName, SamplePerson::setLastName);
        binder.forField(email)
            .withValidator(new EmailValidator("Must be a valid email address", false))
            .bind(SamplePerson::getEmail, SamplePerson::setEmail);
        binder.bind(phone, SamplePerson::getFirstName, SamplePerson::setFirstName);
        binder.bind(dateOfBirth, SamplePerson::getDateOfBirth, SamplePerson::setDateOfBirth);
        binder.bind(occupation, SamplePerson::getOccupation, SamplePerson::setOccupation);
        binder.bind(role, SamplePerson::getRole, SamplePerson::setRole);
        binder.bind(important, SamplePerson::isImportant, SamplePerson::setImportant);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.samplePerson == null)
					this.samplePerson = new SamplePerson();
                binder.writeBean(this.samplePerson);
                samplePersonService.update(this.samplePerson);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(MasterDetailView.class);
            }
            catch (ObjectOptimisticLockingFailureException exception) {
				var n = Notification.show("Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });

        // Create UI
		var splitLayout = new SplitLayout();
        splitLayout.addToPrimary(createGridLayout());
        splitLayout.addToSecondary(createEditorLayout());

        add(splitLayout);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        grid.setItems(
            query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))
            ).stream()
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(Long::parseLong);
        if (samplePersonId.isPresent()) {
            Optional<SamplePerson> samplePersonFromBackend = samplePersonService.get(samplePersonId.get());
            if (samplePersonFromBackend.isPresent())
				populateForm(samplePersonFromBackend.get());
            else {
                Notification.show(
                    String.format("The requested samplePerson was not found, ID = %s", samplePersonId.get()),
                    3000,
                    Notification.Position.BOTTOM_START
                );
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailView.class);
            }
        }
    }

    private Div createEditorLayout() {
		var editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

		var editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, email, phone, dateOfBirth, occupation, role, important);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        return editorLayoutDiv;
    }

    private void createButtonLayout(Div editorLayoutDiv) {
		var buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private Grid<SamplePerson> createGridLayout() {
		var wrapper = new Div(grid);
        wrapper.setClassName("grid-wrapper");
        wrapper.add(grid);
        return grid;
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(SamplePerson value) {
        this.samplePerson = value;
        binder.readBean(this.samplePerson);
    }
}
