package de.dc.csv.formatter.ui.control

import com.google.common.base.Charsets
import com.google.common.io.Files
import de.dc.csv.formatter.ui.model.DateFormat
import de.dc.csv.formatter.ui.provider.FieldsLabelProvider
import java.io.File
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.e4.xwt.DefaultLoadingContext
import org.eclipse.e4.xwt.IConstants
import org.eclipse.e4.xwt.IXWTLoader
import org.eclipse.e4.xwt.XWT
import org.eclipse.e4.xwt.annotation.UI
import org.eclipse.e4.xwt.forms.XWTForms
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.FileDialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableColumn
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Text
import org.eclipse.jface.viewers.IStructuredSelection
import java.text.SimpleDateFormat
import java.util.Locale
import de.dc.csv.formatter.ui.provider.FormatFromTextEditingSupport
import de.dc.csv.formatter.ui.provider.FormatToTextEditingSupport

class CsvComposite extends Composite {

	@UI Text separatorText
	@UI TableViewer fieldsViewer
	@UI Table expireDatesTable
	@UI TableViewerColumn formatColumn
	@UI TableViewerColumn formatColumnTo

	new(Composite parent, int style) {
		super(parent, style)
		setLayout(new FillLayout)
		var name = typeof(CsvComposite).simpleName + IConstants::XWT_EXTENSION_SUFFIX
		var url = typeof(CsvComposite).getResource(name)
		var options = new HashMap<String, Object>()
		options.put(IXWTLoader::CLASS_PROPERTY, this)
		options.put(IXWTLoader::CONTAINER_PROPERTY, this)
		XWT::setLoadingContext(new DefaultLoadingContext(class.classLoader))
		XWTForms::loadWithOptions(url, options)

		fieldsViewer.labelProvider = new FieldsLabelProvider
		formatColumn.editingSupport = new FormatFromTextEditingSupport(fieldsViewer)
		formatColumnTo.editingSupport = new FormatToTextEditingSupport(fieldsViewer)
	}

	def onAdaptButtonSelection(Event e){
		val sel = fieldsViewer.selection as IStructuredSelection
		val selectionIndex = fieldsViewer.table.selectionIndex
		println(selectionIndex)
		if(sel.firstElement instanceof DateFormat){
			val df = sel.firstElement as DateFormat
			val format1 = new SimpleDateFormat(df.formatFrom, Locale::ENGLISH)
			val format2 = new SimpleDateFormat(df.formatTo, Locale::ENGLISH)

			expireDatesTable.items.forEach[item, i |
				if(i>0){
					val date = format1.parse(item.getText(selectionIndex))
					val dateText = format2.format(date)
					item.setText(selectionIndex, dateText)
				}
			]
		}
	}

	def onOpenCsvPathButtonSelection(Event e){
		clearControls

		val dialog = new FileDialog(new Shell, SWT::OPEN)
		val path = dialog.open
		if(path!=null){
			val lines = Files::readLines(new File(path), Charsets::UTF_8)
			val fields = lines.get(0).split(separatorText.text)
			val dateFormats = new ArrayList

			fields.forEach[f|
				dateFormats += new DateFormat(f, 'dd.MM.yyyy', 'dd.MM.yyyy')
				addColumn
			]

			fieldsViewer.input = dateFormats
			lines.forEach[line| line.split(separatorText.text).addItem]
		}
	}

	def clearControls() {
		expireDatesTable.columns.forEach[dispose]
		expireDatesTable.items.forEach[dispose]
	}

	def addItem(String[] cols){
		val item = new TableItem(expireDatesTable, SWT.NULL);
		cols.forEach[c,i|item.setText(i,c)]
	}

	def addColumn(){
		val column = new TableColumn(expireDatesTable, SWT.LEFT)
		column.setWidth(100)
	}
}
