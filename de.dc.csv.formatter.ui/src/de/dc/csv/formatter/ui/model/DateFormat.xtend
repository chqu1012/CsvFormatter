package de.dc.csv.formatter.ui.model

import org.eclipse.xtend.lib.annotations.Accessors

class DateFormat {

	@Accessors String field
	@Accessors String formatFrom
	@Accessors String formatTo

	new(String field, String formatFrom, String formatTo) {
		this.field=field
		this.formatFrom=formatFrom
		this.formatTo=formatTo
	}

}