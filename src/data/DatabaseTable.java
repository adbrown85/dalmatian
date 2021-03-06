/*
 * DatabaseTable.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package data;

import java.awt.Component;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * GUI for viewing a database table.
 */
public class DatabaseTable extends JTable {
	
	private static final int PADDING = 35;
	
	private DatabaseTableModel tableModel;
	private Object[] longestValues;
	
	public DatabaseTable(String sql) throws SQLException {
		super(new DatabaseTableModel(sql));
		init();
	}
	
	public DatabaseTable(String sql,
	                     String[] hiddenColumns)
	                     throws SQLException {
		super(new HidableDatabaseTableModel(sql, hiddenColumns));
		init();
	}
	
	public void add(Object[] row) {
		tableModel.add(row);
	}
	
	private int calculateWidthOfColumn(int index) {
		
		int cellWidth, headerWidth;
		Component component;
		TableCellRenderer renderer;
		
		// Get width of cells
		renderer = getDefaultRenderer(tableModel.getColumnClass(index));
		component = renderer.getTableCellRendererComponent(
		      this, longestValues[index], false, false, 0, index);
		cellWidth = PADDING + component.getPreferredSize().width;
		
		// Get width of header
		renderer = getTableHeader().getDefaultRenderer();
		component = renderer.getTableCellRendererComponent(
		      null, tableModel.getColumnName(index), false, false, 0, 0);
		headerWidth = PADDING + component.getPreferredSize().width;
		
		// Return maximum
		return Math.max(cellWidth, headerWidth);
	}
	
	public String getSQL() {
		return tableModel.getSQL();
	}
	
	public Object getValueAt(int row, String columnName) {
		return tableModel.getValueAt(row, columnName);
	}
	
	private void init() {
		
		this.tableModel = (DatabaseTableModel)dataModel;
		initLongestValues();
		initWidths();
	}
	
	private void initLongestValues() {
		
		Object value;
		int length;
		int[] lengths;
		
		// Initialize
		longestValues = new Object[getColumnCount()];
		lengths = new int[getColumnCount()];
		
		// Store longest values
		for (int r=0; r<getRowCount(); ++r) {
			for (int c=0; c<getColumnCount(); ++c) {
				value = getValueAt(r, c);
				length = value.toString().length();
				if (length > lengths[c]) {
					lengths[c] = length;
					longestValues[c] = value;
				}
			}
		}
	}
	
	private void initWidths() {
		
		TableColumn column;
		TableColumnModel columnModel;
		
		// Set preferred widths of columns
		columnModel = getColumnModel();
		for (int i=0; i<tableModel.getColumnCount(); ++i) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(calculateWidthOfColumn(i));
		}
	}
	
	public void refresh() throws SQLException {
		tableModel.refresh();
		initLongestValues();
		initWidths();
		resizeAndRepaint();
	}
	
	public void remove(int row) {
		tableModel.remove(row);
	}
	
	public void setSQL(String sql) {
		tableModel.setSQL(sql);
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("DatabaseTable");
		String sql = "SELECT id,sponsor,title,year FROM spot";
		String[] hiddenColumns = {"year"};
		
		frame.setContentPane(new DatabaseTable(sql, hiddenColumns));
		frame.pack();
		frame.setVisible(true);
	}
}

