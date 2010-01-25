/*
 * SlotTableModel.java
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
public class SlotTableModel extends HidableDatabaseTableModel {
	
	Break _break;
	
	
	public SlotTableModel(Break _break)
	                      throws SQLException {
		
		super(getSQL(_break), getHiddenColumnNames());
		this._break = _break;
	}
	
	
	public void add(Spot spot) {
		
		Object[] row;
		
		// Position, sponsor, title, year
		row = new Object[5];
		row[0] = ++rowCount;
		row[1] = spot.getId();
		row[2] = spot.getSponsor();
		row[3] = spot.getTitle();
		row[4] = spot.getYear();
		add(row);
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
		
		int breakId;
		String values="";
		
		breakId = _break.getId();
		for (int i=0; i<data.size(); ++i) {
			values += String.format("(%d, %d, %d)",
			                        breakId, i+1, getValueAt(i,"spot"));
			if (i < data.size()-1) {
				values += ", ";
			}
		}
		return values;
	}
	
	
	public void refresh()
	                    throws SQLException {
		
		// Reinitialize data
		this.sql = getSQL(_break);
		super.refresh();
	}
	
	
	public void remove(int rowIndex) {
		
		for (int i=rowIndex+1; i<rowCount; ++i) {
			data.get(i)[0] = i;
		}
		super.remove(rowIndex);
	}
	
	
	public void setBreak(Break _break) {
		
		this._break = _break;
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		JTable table;
		SlotTableModel tableModel;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotTableModel");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame with table
			frame = new JFrame("SlotTableModel");
			tableModel = new SlotTableModel(new Break(12));
			table = new JTable(tableModel);
			frame.setContentPane(table);
			frame.pack();
			frame.setVisible(true);
			
			// Test add
			System.out.println("Waiting to add...");
			Thread.sleep(2000);
			System.out.println("Adding...");
			tableModel.add(new Spot(19));
			frame.pack();
			System.out.println("Now has rows: " + tableModel.getRowCount());
			System.out.println(tableModel.getValues());
			
			// Test commit
			System.out.println("Waiting to commit...");
			Thread.sleep(2000);
			System.out.println("Committing...");
			tableModel.commit();
			
			// Test remove
			System.out.println("\nWaiting to remove...");
			Thread.sleep(5000);
			System.out.println("Removing...");
			tableModel.remove(1);
			frame.pack();
			System.out.println("Now has rows: " + tableModel.getRowCount());
			System.out.println(tableModel.getValues());
			
			// Test commit
			System.out.println("Waiting to commit...");
			Thread.sleep(2000);
			System.out.println("Committing...");
			tableModel.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotTableModel");
		System.out.println("****************************************");
		System.out.println();
	}
}

