/*
 * AppLayout  2024-12-19
 *
 * Copyright (c) Pro Data GmbH & ASA KG. All rights reserved.
 */

package it.prodata.views.gridlayout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * AppLayout
 * @author Robert Pattis
 * @since 2024-12-19
 */
public class AppLayout extends Composite<GridLayout> implements HasSize {

	private H1 header;
	private VerticalLayout sideBar;
	private Component content;
	private Div footer;

	@Override
	protected GridLayout initContent() {
		var content = new GridLayout();
		content.setGridTemplateAreas(
			"header header",
			"sidebar content",
			"footer footer"
		);
		content.setGridTemplateRows("auto 1fr auto");
		content.setGridTemplateColumns("200px 1fr");
		return content;
	}

	public void setTitle(String title) {
		if (header != null)
			header.setText(title);
		if (header == null) {
			GridLayout gridLayout = getContent();
			gridLayout.add(header = new H1(title));
            header.getStyle()
				.setPadding("10px")
				.setBorderBottom("1px solid black");
			gridLayout.setGridArea("header", this.header);
		}
	}

	public void addToSideBar(Component... components) {
		if (sideBar == null) {
			GridLayout gridLayout = getContent();
			gridLayout.add(sideBar = new VerticalLayout());
			sideBar.getStyle()
				.setBorderRight("1px solid black");
			gridLayout.setGridArea("sidebar", gridLayout);
		}
		sideBar.add(components);
	}

	public void setContent(Component content) {
		GridLayout gridLayout = getContent();
		if (this.content != null)
			gridLayout.remove(this.content);
		this.content = content;
		gridLayout.add(content);
		gridLayout.setGridArea("content", content);
	}

	public void setFooter(Component footer) {
		GridLayout gridLayout = getContent();
		if (this.footer != null)
			this.footer.removeAll();
		else {
			gridLayout.add(this.footer = new Div());
			this.footer.getStyle()
				.setBorderTop("1px solid black")
				.setPadding("10px");
			gridLayout.setGridArea("footer", this.footer);
		}
		this.footer.add(footer);
	}
}
