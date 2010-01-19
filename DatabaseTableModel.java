/*
 * DatabaseTableModel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;



/**
 * TableModel for viewing sponsors.
 */
public class DatabaseTableModel extends AbstractTableModel {
	
	private int columnCount, rowCount;
	private int[] columnWidths;
	private ResultSet results;
	private ResultSetMetaData metaData;
	private String sql;
	private String[] columnNames;
	private Vector<Object[]> data;
	public Object[] longestValues;
	
	
	/**
	 * Creates a new %DatabaseTableModel.
	 */
	public DatabaseTableModel(String sql)
	                          throws SQLException {
		
		// Initialize
		super();
		this.sql = sql;
		init();
	}
	
	
	public int getRowCount() {
		
		return rowCount;
	}
	
	
	public int getColumnCount() {
		
		return columnNames.length;
	}
	
	
	public String getColumnName(int columnIndex) {
		
		return columnNames[columnIndex];
	}
	
	
	public int getColumnWidth(int columnIndex) {
		
		return columnWidths[columnIndex];
	}
	
	
	public Object getValueAt(int row,
	                         int column) {
		
		return data.get(row)[column];
	}
	
	
	private void init()
	                  throws SQLException {
		
		initResults();
		initData();
		initColumnNames();
		initColumnWidths();
		initLongestValues();
	}
	
	
	/**
	 * Initializes the column names and number of columns.
	 */
	private void initColumnNames()
	                             throws SQLException {
		
		String columnName;
		
		// Store names from metadata
		columnNames = new String[columnCount];
		for (int i=0; i<columnCount; ++i) {
			columnName = metaData.getColumnName(i+1);
			columnNames[i] = Formatter.toTitleCase(columnName);
		}
	}
	
	
	private void initColumnWidths()
	                             throws SQLException {
		
		// Store widths from metadata
		columnWidths = new int[columnCount];
		for (int i=0; i<columnCount; ++i) {
			columnWidths[i] = metaData.getColumnDisplaySize(i+1);
		}
	}
	
	
	/**
	 * Initializes all the data.
	 */
	private void initData()
	                      throws SQLException {
		
		Object[] row;
		int type;
		
		// Fill with results
		data = new Vector<Object[]>();
		while (results.next()) {
			row = new Object[columnCount];
			for (int i=0; i<columnCount; ++i) {
				type = metaData.getColumnType(i+1);
				if (type == Types.DATE || type == Types.INTEGER)
					row[i] = results.getInt(i+1);
				else
					row[i] = results.getString(i+1);
			}
			data.add(row);
		}
		rowCount = data.size();
	}
	
	
	private void initLongestValues() {
		
		Object[] row;
		int length;
		int[] lengths;
		
		// Initialize
		longestValues = new Object[columnCount];
		lengths = new int[columnCount];
		for (int c=0; c<columnCount; ++c) {
			lengths[c] = 0;
		}
		
		// Store longest values
		for (int r=0; r<rowCount; ++r) {
			for (int c=0; c<columnCount; ++c) {
				row = data.get(r);
				length = row[c].toString().length();
				if (length > lengths[c]) {
					lengths[c] = length;
					longestValues[c] = row[c];
				}
			}
		}
	}
	
	
	private void initResults()
	                         throws SQLException {
		
		// Execute query
		results = Database.executeQuery(sql);
		metaData = results.getMetaData();
		columnCount = metaData.getColumnCount();
	}
	
	
	/**
	 * Refreshes the data.
	 */
	public void refresh()
	                    throws SQLException {
		
		// Reinitialize data
		init();
		fireTableDataChanged();
	}
	
	
	/**
	 * Tests the %DatabaseTableModel.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		JTable table;
		String sql;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DatabaseTableModel");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame and add table
			frame = new JFrame("DatabaseTableModel");
			sql = "SELECT * FROM sponsor";
			table = new JTable(new DatabaseTableModel(sql));
			frame.setContentPane(table);
			frame.pack();
			frame.setVisible(true);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DatabaseTableModel");
		System.out.println("****************************************");
		System.out.println();
	}
}

