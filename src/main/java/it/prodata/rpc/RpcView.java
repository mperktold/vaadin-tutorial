
package it.prodata.rpc;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Remote Procedure Calls")
@Route("rpc")
@Menu(order = 0, icon = LineAwesomeIconUrl.TTY_SOLID)
@JsModule("src/rpc.ts")
@Tag("g14-rpc")
public class RpcView extends Component {

	public RpcView() {
		var callClient = new Button("callClient", e -> callClient());
		getElement().appendChild(callClient.getElement());
		getElement().setProperty("name", "Matthias");
	}

	private void callClient() {
		getElement().executeJs("this.method()");
	}

	@ClientCallable
	private static void method() {
		Notification.show("Server method called");
	}
}
