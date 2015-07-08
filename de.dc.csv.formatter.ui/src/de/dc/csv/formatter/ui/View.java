package de.dc.csv.formatter.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.dc.csv.formatter.ui.control.CsvComposite;

public class View extends ViewPart {
	public static final String ID = "de.dc.csv.formatter.ui.view";

	public void createPartControl(Composite parent) {
		new CsvComposite(parent,0);
	}

	public void setFocus() {
	}
}