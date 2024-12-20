import { css, CSSResultGroup, html, LitElement } from "lit";
import { customElement, property } from "lit/decorators.js";

@customElement(Rpc.is)
export class Rpc extends LitElement {

	static is = "g14-rpc";

	@property({ type: String })
	name = "World";

	static get styles(): CSSResultGroup {
		return css`
			.name {
				color: red;
			}
		`;
	}

	protected render(): unknown {
		return html`
			<span>Hello</span>
			<span class="name">${this.name}</span>
			<button @click=${this.callServer}>callServer</button>
			<slot></slot>
		`;
	}

	method() {
		alert("Client method called");
	}

	private callServer() {
		const server = (this as any).$server;
		server.method();
	}
}
