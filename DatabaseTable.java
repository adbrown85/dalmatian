/*
 * DatabaseTable.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.Component;
import java.awt.Dimension;
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
	
	private static final int PADDING=35;
	private String tableName;
	private DatabaseTableModel tableModel;
	
	
	/**
	 * Creates a new %DatabaseTable.
	 */
	public DatabaseTable(String tableName)
	                     throws SQLException {
		
		// Initialize
		super(new DatabaseTableModel(tableName));
		this.tableModel = (DatabaseTableModel)dataModel;
		this.tableName = tableName;
		initWidths();
	}
	
	
	
	private int calculateWidthOfColumn(int index) {
		
		int cellWidth, headerWidth;
		Component component;
		TableCellRenderer renderer;
		
		// Get width of cells
		renderer = getDefaultRenderer(tableModel.getColumnClass(index));
		component = renderer.getTableCellRendererComponent(
		      this, tableModel.longestValues[index], false, false, 0, index);
		cellWidth = PADDING + component.getPreferredSize().width;
		
		// Get width of header
		renderer = getTableHeader().getDefaultRenderer();
		component = renderer.getTableCellRendererComponent(
		      null, tableModel.getColumnName(index), false, false, 0, 0);
		headerWidth = PADDING + component.getPreferredSize().width;
		
		// Return maximum
		return Math.max(cellWidth, headerWidth);
	}
	
	
/*
	public Dimension getPreferredScrollableViewportSize() {
		
		return getPreferredSize();
	}
*/
	
	
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
	
	
	/**
	 * Refreshes the data in the table.
	 */
	public void refresh()
	                    throws SQLException {
		
		tableModel.refresh();
		initWidths();
	}
	
	
	/**
	 * Test for %DatabaseTable.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		String sql;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DatabaseTable");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		try {
			frame = new JFrame("DatabaseTable");
			sql = "SELECT id, sponsor, name, year FROM spot";
			frame.setContentPane(new DatabaseTable(sql));
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DatabaseTable");
		System.out.println("****************************************");
		System.out.println();
	}
}

