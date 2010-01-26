/*
 * SlotTable.java
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
 * Table for viewing sponsors.
 */
public class SlotTable extends DatabaseTable {
	
	Break _break;
	
	
	public SlotTable(Break _break)
	                 throws SQLException {
		
		super(getSQL(_break), getHiddenColumnNames());
		
		// Initialize
		this._break = _break;
	}
	
	
	public void add(Spot spot) {
		
		Object[] row;
		
		// Add row with position, id, sponsor, title, year
		row = new Object[5];
		row[0] = getRowCount() + 1;
		row[1] = spot.getId();
		row[2] = spot.getSponsor();
		row[3] = spot.getTitle();
		row[4] = spot.getYear();
		super.add(row);
	}
	
	
	public void commit()
	                   throws SQLException {
		
		String sql;
		
		// Start transation
		Database.executeUpdate("START TRANSACTION");
		
		// Insert break, or update break and clear slots
		if (_break.getId() == 0) {
			_break.insert();
		} {
			Break.update(_break, _break);
			sql = "  DELETE FROM slot WHERE break=" + _break.getId();
			Database.executeUpdate(sql);
		}
		
		// Insert slots back into the break and commit
		sql = "  INSERT INTO slot(break,position,spot) " + 
		      "  VALUES " + getValues();
		Database.executeUpdate(sql);
		
		// Commit
		Database.executeUpdate("COMMIT");
	}
	
	
	private static String getSQL(Break _break) {
		
		return "SELECT position,spot,sponsor,title,year " +
		       "FROM view_slot " +
		       "WHERE break=" + _break.getId() + " " +
		       "ORDER BY position";
	}
	
	
	private static String[] getHiddenColumnNames() {
		
		String[] hiddenColumnNames={"spot"};
		
		return hiddenColumnNames;
	}
	
	
	private String getValues() {
		
		int breakId, rowCount;
		String values="";
		
		breakId = _break.getId();
		rowCount = getRowCount();
		for (int i=0; i<rowCount; ++i) {
			values += String.format("(%d, %d, %d)",
			                        breakId,
			                        i+1,
			                        getValueAt(i,"spot"));
			if (i < rowCount-1) {
				values += ", ";
			}
		}
		return values;
	}
	
	
	public void refresh()
	                    throws SQLException {
		
		// Reinitialize data
		setSQL(getSQL(_break));
		super.refresh();
	}
	
	
	public void remove(int rowIndex) {
		
		int rowCount;
		
		rowCount = getRowCount();
		for (int i=rowIndex+1; i<rowCount; ++i) {
			setValueAt(i, i, 0);
		}
		super.remove(rowIndex);
	}
	
	
	public void setBreak(Break _break) {
		
		this._break = _break;
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SlotTable table;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotTable");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame with table
			frame = new JFrame("SlotTable");
			table = new SlotTable(new Break(12));
			frame.setContentPane(table);
			frame.pack();
			frame.setVisible(true);
			
			// Test add
			System.out.println("Waiting to add...");
			Thread.sleep(2000);
			System.out.println("Adding...");
			table.add(new Spot(19));
			frame.pack();
			System.out.println("Now has rows: " + table.getRowCount());
			System.out.println(table.getValues());
			
			// Test commit
			System.out.println("Waiting to commit...");
			Thread.sleep(2000);
			System.out.println("Committing...");
			table.commit();
			
			// Test remove
			System.out.println("\nWaiting to remove...");
			Thread.sleep(5000);
			System.out.println("Removing...");
			table.remove(1);
			frame.pack();
			System.out.println("Now has rows: " + table.getRowCount());
			System.out.println(table.getValues());
			
			// Test commit
			System.out.println("Waiting to commit...");
			Thread.sleep(2000);
			System.out.println("Committing...");
			table.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotTable");
		System.out.println("****************************************");
		System.out.println();
	}
}

