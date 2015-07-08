package de.dc.csv.formatter.ui.provider

import org.eclipse.jface.viewers.LabelProvider
import org.eclipse.jface.viewers.ITableLabelProvider
import de.dc.csv.formatter.ui.model.DateFormat

class FieldsLabelProvider extends LabelProvider implements ITableLabelProvider {

	override getColumnImage(Object element, int columnIndex) {
	}

	override getColumnText(Object element, int columnIndex) {
		val df = element as DateFormat
		switch columnIndex{
			case 0:
				return df.field
			case 1:
				return df.formatFrom
			case 2:
				return df.formatTo
		}
	}

}