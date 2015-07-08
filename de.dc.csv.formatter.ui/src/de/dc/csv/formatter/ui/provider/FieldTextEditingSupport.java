package de.dc.csv.formatter.ui.provider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import de.dc.csv.formatter.ui.model.DateFormat;

public class FieldTextEditingSupport extends EditingSupport {

  private final TableViewer viewer;
  private final CellEditor editor;

  public FieldTextEditingSupport(TableViewer viewer) {
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
    return ((DateFormat) element).getField();
  }

  @Override
  protected void setValue(Object element, Object userInputValue) {
    ((DateFormat) element).setField(String.valueOf(userInputValue));
    viewer.update(element, null);
  }
}