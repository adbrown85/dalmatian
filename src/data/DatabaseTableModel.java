/*
 * DatabaseTableModel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


/**
 * TableModel for viewing data from a database.
 */
public class DatabaseTableModel extends AbstractTableModel {
	
	protected int columnCount, rowCount;
	protected TreeMap<String,Integer> columnNameToIndex;
	protected ResultSet results;
	protected ResultSetMetaData metaData;
	protected String sql;
	protected String[] columnNames;
	protected Vector<Object[]> data;
	
	public DatabaseTableModel(String sql) throws SQLException {
		
		super();
		
		initResultsMetaData(sql);
		initColumnCount();
		initColumnNames();
		initData();
	}
	
	public void add(Object[] row) {
		data.add(row);
		++rowCount;
		fireTableRowsInserted(rowCount, rowCount);
	}
	
   public void refresh() throws SQLException {
      initResultsMetaData(this.sql);
      initData();
      fireTableDataChanged();
   }
   
   public void remove(int row) {
      data.removeElementAt(row);
      --rowCount;
      fireTableRowsDeleted(row, row);
   }
	
	//------------------------------------------------------------
   // Helpers
   //
	
   private void initColumnCount() throws SQLException {
      columnCount = metaData.getColumnCount();
   }
   
   private void initColumnNames() throws SQLException {
      
      String columnName;
      
      // Store names from metadata
      columnNames = new String[columnCount];
      columnNameToIndex = new TreeMap<String,Integer>();
      for (int i=0; i<columnCount; ++i) {
         columnName = metaData.getColumnName(i+1);
         columnNames[i] = columnName;
         columnNameToIndex.put(columnName, i);
      }
   }
   
   private void initData() throws SQLException {
      
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
   
   private void initResultsMetaData(String sql) throws SQLException {
      this.sql = sql;
      results = Database.executeQuery(sql);
      metaData = results.getMetaData();
   }
	
	//------------------------------------------------------------
   // Getters and setters
   //

   public int getRowCount() {
      return rowCount;
   }
   
   public int getColumnCount() {
      return columnCount;
   }
   
   protected int getColumnIndex(String columnName) {
      return columnNameToIndex.get(columnName);
   }
   
   public String getColumnName(int columnIndex) {
      return columnNames[columnIndex];
   }
   
   public String getSQL() {
      return sql;
   }
   
   public Object getValueAt(int row, int column) {
      return data.get(row)[column];
   }
   
   public Object getValueAt(int row, String columnName) {
      
      int columnIndex;
      
      columnIndex = columnNameToIndex.get(columnName);
      return data.get(row)[columnIndex];
   }
	
   public void setSQL(String sql) {
      this.sql = sql;
   }
   
   public void setValueAt(Object value, int row, int column) {
      data.get(row)[column] = value;
      fireTableCellUpdated(row, column);
   }
   
	//------------------------------------------------------------
   // Main
   //
   
   public static void main(String[] args) {
		
		DatabaseTableModel tableModel;
		JFrame frame;
		JScrollPane scrollPane;
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
			tableModel = new DatabaseTableModel(sql);
			table = new JTable(tableModel);
			scrollPane = new JScrollPane(table);
			frame.setContentPane(scrollPane);
			frame.pack();
			frame.setVisible(true);
			
			// Check actual values
			System.out.println(tableModel.getValueAt(0, "name"));
			System.out.println(tableModel.getValueAt(0, "street"));
			System.out.println(tableModel.getValueAt(0, "city"));
			System.out.println(tableModel.getValueAt(0, "state"));
			System.out.println(tableModel.getValueAt(0, "zip"));
			System.out.println(tableModel.getValueAt(0, "phone"));
		
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

