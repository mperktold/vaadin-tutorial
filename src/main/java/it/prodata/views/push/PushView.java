package it.prodata.views.push;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Push")
@Route("push")
@Menu(order = 0, icon = LineAwesomeIconUrl.PUSHED)
public class PushView extends VerticalLayout {

	private final Button startInBackground = new Button("Start in Background");
	private final Button runDirectly = new Button("Run directly");
	private final Span statusLabel = new Span();
	private final ProgressBar progressBar = new ProgressBar();

	public PushView() {
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		add(
			new HorizontalLayout(startInBackground, runDirectly),
			statusLabel,
			progressBar
		);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		startInBackground.addClickListener(e -> startInBackground(attachEvent.getUI()));
		runDirectly.addClickListener(e -> runDirectly());
	}

	private void startInBackground(UI ui) {
		progressBar.setVisible(true);
		Thread.startVirtualThread(() -> performBackgroundProcess(ui));
	}

	private void performBackgroundProcess(UI ui) {
		ui.access(() -> statusLabel.setText("Started"));
		// Simulating long running process...
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
		ui.access(() -> {
			statusLabel.setText("Completed");
			progressBar.setVisible(false);
		});
	}

	private void runDirectly() {
		statusLabel.setText("Started");
		// Simulating long running process...
		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
		statusLabel.setText("Completed");
		progressBar.setVisible(false);
	}
}
