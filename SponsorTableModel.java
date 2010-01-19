/*
 * SponsorTableModel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;



/**
 * TableModel for viewing sponsors.
 */
public class SponsorTableModel extends AbstractTableModel {
	
	private Object[][] data;
	private Vector<String> columnNames;
	
	
	public SponsorTableModel()
	                         throws SQLException {
		
		// Initialize
		super();
		initColumnNames();
		initData();
	}
	
	
	public int getRowCount() {
		return data.length;
	}
	
	public int getColumnCount() {
		return columnNames.size();
	}
	
	public String getColumnName(int columnIndex) {
		return columnNames.get(columnIndex);
	}
	
	public Object getValueAt(int row,
	                         int column) {
		
		return data[row][column];
	}
	
	
	/**
	 * Initializes the column names.
	 */
	private void initColumnNames() {
		
		columnNames = new Vector<String>();
		columnNames.add("Name");
		columnNames.add("Street");
		columnNames.add("City");
		columnNames.add("State");
		columnNames.add("Zip");
	}
	
	
	/**
	 * Initializes all the data.
	 */
	private void initData()
	                      throws SQLException {
		
		int row, numberOfColumns, numberOfRows;
		ResultSet results;
		ResultSetMetaData resultsMetaData;
		String sql;
		
		// Execute query
		sql = "SELECT * FROM sponsor ORDER BY name";
		results = Database.executeQuery(sql);
		
		// Count rows and columns
		resultsMetaData = results.getMetaData();
		numberOfColumns = resultsMetaData.getColumnCount();
		numberOfRows = 0;
		while (results.next())
			++numberOfRows;
		
		// Allocate
		ids = new int[numberOfRows];
		data = new Object[numberOfRows][];
		for (int i=0; i<numberOfRows; ++i)
			data[i] = new String[numberOfColumns];
		
		// Fill
		results.beforeFirst();
		row = -1;
		while (results.next()) {
			++row;
			data[row][0] = results.getString("name");
			data[row][1] = results.getString("street");
			data[row][2] = results.getString("city");
			data[row][3] = results.getString("state");
			data[row][4] = results.getString("zip");
		}
	}
	
	
	/**
	 * Refreshes the data.
	 */
	public void refresh()
	                    throws SQLException {
		
		// Reinitialize data
		initData();
		fireTableDataChanged();
	}
	
	
	/**
	 * Tests the %SponsorTableModel.
	 */
	public static void main(String[] args) {
		
		SponsorTableModel stm;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorTableModel");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		try {
			stm = new SponsorTableModel();
			System.out.printf("Number of columns: %d\n", stm.getColumnCount());
			System.out.printf("Number of rows: %d\n", stm.getRowCount());
			System.out.printf("First sponsor: %s\n", stm.getValueAt(0,0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorTableModel");
		System.out.println("****************************************");
		System.out.println();
	}
}

