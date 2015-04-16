package com.kg.demo.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.kg.demo.service.SidebarPage;
import com.kg.demo.service.SidebarPageConfig;
import com.kg.demo.service.impl.SidebarPageConfigAjaxBasedImpl;

public class SidebarAjaxbasedController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	Grid fnList;

	SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		Rows rows = fnList.getRows();

		for (SidebarPage page : pageConfig.getPages()) {
			Row row = constructSidebarRow(page.getName(), page.getLabel(),
					page.getIconUri(), page.getUri());
			rows.appendChild(row);
		}
	}

	private Row constructSidebarRow(final String name, String label,
			String imageSrc, final String locationUri) {

		Row row = new Row();
		Image image = new Image(imageSrc);
		Label lab = new Label(label);

		row.appendChild(image);
		row.appendChild(lab);

		row.setSclass("sidebar-fn");

		EventListener<Event> onActionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {

				if (locationUri.startsWith("http")) {

					Executions.getCurrent().sendRedirect(locationUri);
				} else {

					Include include = (Include) Selectors
							.iterable(fnList.getPage(), "#mainInclude")
							.iterator().next();
					include.setSrc(locationUri);

					if (name != null) {
						getPage().getDesktop().setBookmark("p_" + name);
					}
				}
			}
		};
		row.addEventListener(Events.ON_CLICK, onActionListener);

		return row;
	}

}
