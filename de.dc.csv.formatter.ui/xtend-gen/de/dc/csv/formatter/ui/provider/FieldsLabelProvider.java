package de.dc.csv.formatter.ui.provider;

import de.dc.csv.formatter.ui.model.DateFormat;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings("all")
public class FieldsLabelProvider extends LabelProvider implements ITableLabelProvider {
  @Override
  public Image getColumnImage(final Object element, final int columnIndex) {
    return null;
  }
  
  @Override
  public String getColumnText(final Object element, final int columnIndex) {
    final DateFormat df = ((DateFormat) element);
    switch (columnIndex) {
      case 0:
        return df.getField();
      case 1:
        return df.getFormatFrom();
      case 2:
        return df.getFormatTo();
    }
    return null;
  }
}
