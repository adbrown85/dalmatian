/*
 * SponsorTableModel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.ResultSet;
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
	
	
	
	/**
	 * Returns the number of rows in the table.
	 */
	public int getRowCount() {
		
		return data.length;
	}
	
	
	
	/**
	 * Returns the number of columns in the table.
	 */
	public int getColumnCount() {
		
		return columnNames.size();
	}
	
	
	
	/**
	 * Returns the number of columns in the table.
	 */
	public String getColumnName(int columnIndex) {
		
		return columnNames.get(columnIndex);
	}
	
	
	
	/**
	 * Gets the value of a cell.
	 */
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
		String query;
		
		// Execute query
		query = "SELECT s.name, a.street, a.city, a.state, a.zip " +
		        "FROM sponsor AS s JOIN address AS a " +
		        "ON s.address = a.id";
		results = Database.executeQuery(query);
		
		// Count rows and columns
		numberOfColumns = columnNames.size();
		numberOfRows = 0;
		while (results.next())
			++numberOfRows;
		
		// Allocate
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

