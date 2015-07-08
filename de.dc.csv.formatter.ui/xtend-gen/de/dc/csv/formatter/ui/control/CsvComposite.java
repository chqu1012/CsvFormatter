package de.dc.csv.formatter.ui.control;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.Files;
import de.dc.csv.formatter.ui.model.DateFormat;
import de.dc.csv.formatter.ui.provider.FieldsLabelProvider;
import de.dc.csv.formatter.ui.provider.FormatFromTextEditingSupport;
import de.dc.csv.formatter.ui.provider.FormatToTextEditingSupport;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import org.eclipse.e4.xwt.DefaultLoadingContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.IXWTLoader;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.annotation.UI;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class CsvComposite extends Composite {
  @UI
  private Text separatorText;
  
  @UI
  private TableViewer fieldsViewer;
  
  @UI
  private Table expireDatesTable;
  
  @UI
  private TableViewerColumn formatColumn;
  
  @UI
  private TableViewerColumn formatColumnTo;
  
  public CsvComposite(final Composite parent, final int style) {
    super(parent, style);
    try {
      FillLayout _fillLayout = new FillLayout();
      this.setLayout(_fillLayout);
      String _simpleName = CsvComposite.class.getSimpleName();
      String name = (_simpleName + IConstants.XWT_EXTENSION_SUFFIX);
      URL url = CsvComposite.class.getResource(name);
      HashMap<String, Object> options = new HashMap<String, Object>();
      options.put(IXWTLoader.CLASS_PROPERTY, this);
      options.put(IXWTLoader.CONTAINER_PROPERTY, this);
      Class<? extends CsvComposite> _class = this.getClass();
      ClassLoader _classLoader = _class.getClassLoader();
      DefaultLoadingContext _defaultLoadingContext = new DefaultLoadingContext(_classLoader);
      XWT.setLoadingContext(_defaultLoadingContext);
      XWTForms.loadWithOptions(url, options);
      FieldsLabelProvider _fieldsLabelProvider = new FieldsLabelProvider();
      this.fieldsViewer.setLabelProvider(_fieldsLabelProvider);
      FormatFromTextEditingSupport _formatFromTextEditingSupport = new FormatFromTextEditingSupport(this.fieldsViewer);
      this.formatColumn.setEditingSupport(_formatFromTextEditingSupport);
      FormatToTextEditingSupport _formatToTextEditingSupport = new FormatToTextEditingSupport(this.fieldsViewer);
      this.formatColumnTo.setEditingSupport(_formatToTextEditingSupport);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void onAdaptButtonSelection(final Event e) {
    ISelection _selection = this.fieldsViewer.getSelection();
    final IStructuredSelection sel = ((IStructuredSelection) _selection);
    Table _table = this.fieldsViewer.getTable();
    final int selectionIndex = _table.getSelectionIndex();
    InputOutput.<Integer>println(Integer.valueOf(selectionIndex));
    Object _firstElement = sel.getFirstElement();
    if ((_firstElement instanceof DateFormat)) {
      Object _firstElement_1 = sel.getFirstElement();
      final DateFormat df = ((DateFormat) _firstElement_1);
      String _formatFrom = df.getFormatFrom();
      final SimpleDateFormat format1 = new SimpleDateFormat(_formatFrom, Locale.ENGLISH);
      String _formatTo = df.getFormatTo();
      final SimpleDateFormat format2 = new SimpleDateFormat(_formatTo, Locale.ENGLISH);
      TableItem[] _items = this.expireDatesTable.getItems();
      final Procedure2<TableItem, Integer> _function = (TableItem item, Integer i) -> {
        try {
          if (((i).intValue() > 0)) {
            String _text = item.getText(selectionIndex);
            final Date date = format1.parse(_text);
            final String dateText = format2.format(date);
            item.setText(selectionIndex, dateText);
          }
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      IterableExtensions.<TableItem>forEach(((Iterable<TableItem>)Conversions.doWrapArray(_items)), _function);
    }
  }
  
  public void onOpenCsvPathButtonSelection(final Event e) {
    try {
      this.clearControls();
      Shell _shell = new Shell();
      final FileDialog dialog = new FileDialog(_shell, SWT.OPEN);
      final String path = dialog.open();
      boolean _notEquals = (!Objects.equal(path, null));
      if (_notEquals) {
        File _file = new File(path);
        final List<String> lines = Files.readLines(_file, Charsets.UTF_8);
        String _get = lines.get(0);
        String _text = this.separatorText.getText();
        final String[] fields = _get.split(_text);
        final ArrayList<DateFormat> dateFormats = new ArrayList<DateFormat>();
        final Consumer<String> _function = (String f) -> {
          DateFormat _dateFormat = new DateFormat(f, "dd.MM.yyyy", "dd.MM.yyyy");
          dateFormats.add(_dateFormat);
          this.addColumn();
        };
        ((List<String>)Conversions.doWrapArray(fields)).forEach(_function);
        this.fieldsViewer.setInput(dateFormats);
        final Consumer<String> _function_1 = (String line) -> {
          String _text_1 = this.separatorText.getText();
          String[] _split = line.split(_text_1);
          this.addItem(_split);
        };
        lines.forEach(_function_1);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void clearControls() {
    TableColumn[] _columns = this.expireDatesTable.getColumns();
    final Consumer<TableColumn> _function = (TableColumn it) -> {
      it.dispose();
    };
    ((List<TableColumn>)Conversions.doWrapArray(_columns)).forEach(_function);
    TableItem[] _items = this.expireDatesTable.getItems();
    final Consumer<TableItem> _function_1 = (TableItem it) -> {
      it.dispose();
    };
    ((List<TableItem>)Conversions.doWrapArray(_items)).forEach(_function_1);
  }
  
  public void addItem(final String[] cols) {
    final TableItem item = new TableItem(this.expireDatesTable, SWT.NULL);
    final Procedure2<String, Integer> _function = (String c, Integer i) -> {
      item.setText((i).intValue(), c);
    };
    IterableExtensions.<String>forEach(((Iterable<String>)Conversions.doWrapArray(cols)), _function);
  }
  
  public void addColumn() {
    final TableColumn column = new TableColumn(this.expireDatesTable, SWT.LEFT);
    column.setWidth(100);
  }
}
