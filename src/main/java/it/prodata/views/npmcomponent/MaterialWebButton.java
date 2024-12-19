/*
 * MaterialWebButton  2024-12-19
 *
 * Copyright (c) Pro Data GmbH & ASA KG. All rights reserved.
 */

package it.prodata.views.npmcomponent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

import jakarta.validation.constraints.NotNull;

/**
 * MaterialWebButton
 * @author Robert Pattis
 * @since 2024-12-19
 */
@Tag("mwc-button")
@NpmPackage(value = "@material/mwc-button", version = "0.27.0")
@JsModule("@material/mwc-button/mwc-button.js")
public class MaterialWebButton extends Component implements HasLabel {

	public enum Type {
		DEFAULT(null),
		OUTLINED("outlined"),
		RAISED("raised"),
		UNELEVATED("unelevated");
		private final String attr;
		Type(String attr) {
			this.attr = attr;
		}
	}

	private @NotNull Type type;

	public MaterialWebButton(String label) {
		setLabel(label);
		type = Type.DEFAULT;
	}

	public Type getType() {
		return type;
	}

	public void setType(@NotNull Type type) {
		if (this.type.attr != null)
			getElement().removeAttribute(this.type.attr);
		this.type = type;
		if (type.attr != null)
			getElement().setAttribute(type.attr, true);
	}

	public boolean isDense() {
		return getElement().hasAttribute("dense");
	}

	public void setDense(boolean dense) {
		getElement().setAttribute("dense", dense);
	}

}
