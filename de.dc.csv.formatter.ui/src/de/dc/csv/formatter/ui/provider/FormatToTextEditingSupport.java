package de.dc.csv.formatter.ui.provider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import de.dc.csv.formatter.ui.model.DateFormat;

public class FormatToTextEditingSupport extends EditingSupport {

  private final TableViewer viewer;
  private final CellEditor editor;

  public FormatToTextEditingSupport(TableViewer viewer) {
    super(viewer);
    this.viewer = viewer;
    this.editor = new TextCellEditor(viewer.getTable());
  }

  @Override
  protected CellEditor getCellEditor(Object element) {
    return editor;
  }

  @Override
  protected boolean canEdit(Object element) {
    return true;
  }

  @Override
  protected Object getValue(Object element) {
    return ((DateFormat) element).getFormatTo();
  }

  @Override
  protected void setValue(Object element, Object userInputValue) {
    ((DateFormat) element).setFormatTo(String.valueOf(userInputValue));
    viewer.update(element, null);
  }
}