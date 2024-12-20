package it.prodata.views.dialogs;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Dialogs")
@Route("dialogs")
@Menu(order = 0, icon = LineAwesomeIconUrl.WINDOWS)
public class DialogsView extends Composite<VerticalLayout> {

	private final Checkbox modal = new Checkbox("Modal");
	private final Checkbox closeOnEsc = new Checkbox("Close on Esc");
	private final Checkbox closeOnOutsideClick = new Checkbox("Close on Outside Click");

	@Override
	protected VerticalLayout initContent() {
		return new VerticalLayout(
			modal,
			closeOnEsc,
			closeOnOutsideClick,
			new Button("Open", this::openDialog)
		);
	}

	private void openDialog(ClickEvent<Button> clickEvent) {
		var dialog = new Dialog();
		dialog.getHeader().add(new H1("Example dialog"));
		dialog.add("Example content");
		dialog.getFooter().add(new Button("Close", e -> dialog.close()));
		dialog.setModal(modal.getValue());
		dialog.setCloseOnEsc(closeOnEsc.getValue());
		dialog.setCloseOnOutsideClick(closeOnOutsideClick.getValue());
		dialog.addDialogCloseActionListener(DialogsView::handleDialogCloseAction);
		dialog.open();
	}

	private static void handleDialogCloseAction(Dialog.DialogCloseActionEvent closeActionEvent) {
		var confirmDialog = new ConfirmDialog(
			"Closing", "Are you sure you want to close the dialog?",
			"Close", e -> closeActionEvent.getSource().close(),
			"Cancel", e -> {}
		);
		confirmDialog.open();
	}
}
