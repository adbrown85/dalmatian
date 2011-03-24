/*
 * HidableDatabaseTableModel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package data;

import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * TableModel for viewing data from a database where columns can be hidden.
 * 
 * <p>Takes an array of column names that should be hidden and makes a
 * mapping of visible columns to the actual columns in the data.  The
 * overridden methods of getValueAt(), getColumnCount(), and
 * getColumnName() then use this mapping.  Values from hidden columns
 * can be obtained by using the actual column name.
 */
public class HidableDatabaseTableModel extends DatabaseTableModel {
	
	protected int columnCountVisible;
	private Vector<Integer> hiddenColumnIndices;
	private int[] visibleToDataColumns;
	
	public HidableDatabaseTableModel(String sql,
	                                 String[] hiddenColumnNames)
	                                 throws SQLException {
		
		super(sql);
		
		// Initialize
		initHiddenColumnIndices(hiddenColumnNames);
		initColumnCountVisible();
		initVisibleToDataColumns();
	}
	
   //------------------------------------------------------------
   // Getters
   //
   
   public int getDataColumn(int columnIndex) {
      return visibleToDataColumns[columnIndex];
   }
   
   public Object getValueAt(int rowIndex,
                            int columnIndex) {
      return data.get(rowIndex)[getDataColumn(columnIndex)];
   }
   
   public int getColumnCount() {
      return columnCountVisible;
   }
   
   public String getColumnName(int columnIndex) {
      return columnNames[getDataColumn(columnIndex)];
   }
   
	//------------------------------------------------------------
   // Helpers
   //
	
   private boolean hasHiddenColumns() {
      return hiddenColumnIndices != null && !hiddenColumnIndices.isEmpty();
   }
   
   private void initColumnCountVisible() {
      if (hasHiddenColumns()) {
         columnCountVisible = columnCount - hiddenColumnIndices.size();
      } else {
         columnCountVisible = columnCount;
      }
   }
   
   private void initHiddenColumnIndices(String[] hiddenColumnNames) {
      
      // Test if empty
      if (hiddenColumnNames == null) {
         hiddenColumnIndices = null;
         return;
      }
      
      // Store indices of hidden columns
      hiddenColumnIndices = new Vector<Integer>();
      for (String columnName : hiddenColumnNames) {
         Integer columnIndex = getColumnIndex(columnName);
         if (columnIndex != null)
            hiddenColumnIndices.add(columnIndex);
      }
   }
   
   private void initVisibleToDataColumns() {
      
      // Allocate
      visibleToDataColumns = new int[columnCountVisible];
      
      // If hidden columns, map visible columns to data columns
      if (hasHiddenColumns()) {
         int visibleColumn = -1;
         for (int i=0; i<columnCount; ++i) {
            if (isColumnVisible(i)) {
               visibleToDataColumns[++visibleColumn] = i;
            }
         }
      }
      
      // Otherwise mapping is straight
      else {
         for (int i=0; i<columnCount; ++i) {
            visibleToDataColumns[i] = i;
         }
      }
   }
   
   private boolean isColumnVisible(int columnIndex) {
      if (hiddenColumnIndices == null)
         return true;
      return !hiddenColumnIndices.contains(columnIndex);
   }
   
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		
		HidableDatabaseTableModel tableModel;
		String[] hiddenColumnNames={"zip"};
		JFrame frame;
		JScrollPane scrollPane;
		JTable table;
		String sql;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("HidableDatabaseTableModel");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame and add table
			frame = new JFrame("DatabaseTableModel");
			sql = "SELECT * FROM sponsor";
			tableModel = new HidableDatabaseTableModel(sql, hiddenColumnNames);
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

