package de.dc.csv.formatter.ui.model;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class DateFormat {
  @Accessors
  private String field;
  
  @Accessors
  private String formatFrom;
  
  @Accessors
  private String formatTo;
  
  public DateFormat(final String field, final String formatFrom, final String formatTo) {
    this.field = field;
    this.formatFrom = formatFrom;
    this.formatTo = formatTo;
  }
  
  @Pure
  public String getField() {
    return this.field;
  }
  
  public void setField(final String field) {
    this.field = field;
  }
  
  @Pure
  public String getFormatFrom() {
    return this.formatFrom;
  }
  
  public void setFormatFrom(final String formatFrom) {
    this.formatFrom = formatFrom;
  }
  
  @Pure
  public String getFormatTo() {
    return this.formatTo;
  }
  
  public void setFormatTo(final String formatTo) {
    this.formatTo = formatTo;
  }
}
