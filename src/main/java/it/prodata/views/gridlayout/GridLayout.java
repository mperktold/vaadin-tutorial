/*
 * GridLayout  2024-12-19
 *
 * Copyright (c) Pro Data GmbH & ASA KG. All rights reserved.
 */

package it.prodata.views.gridlayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Style;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * GridLayout
 * @author Robert Pattis
 * @since 2024-12-19
 */
@Tag(Tag.DIV)
public class GridLayout extends Component implements HasSize, HasComponents {

	public GridLayout() {
		getStyle().setDisplay(Style.Display.GRID);
	}

	public GridLayout(Component... components) {
		this();
		add(components);
	}

	public void setGridTemplateAreas(String... areas) {
		getStyle().set("grid-template-areas", Arrays.stream(areas).map(a -> '\'' + a + '\'').collect(Collectors.joining(" ")));
	}

	public void setGridTemplateRows(String... rows) {
		getStyle().set("grid-template-rows", String.join(" ", rows));
	}

	public void setGridTemplateColumns(String... columns) {
		getStyle().set("grid-template-columns", String.join(" ", columns));
	}

	public void setGap(String gap) {
		getStyle().set("gap", gap);
	}

	public void setGridArea(String area, Component... components) {
		Arrays.stream(components).forEach(c -> c.getStyle().set("grid-area", area));
	}
}
