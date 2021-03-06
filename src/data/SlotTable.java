/*
 * SlotTable.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package data;

import java.sql.SQLException;
import javax.swing.JFrame;


/**
 * Table for viewing sponsors.
 */
public class SlotTable extends DatabaseTable {
	
	Break _break;
	
	public SlotTable(Break _break)
	                 throws SQLException {
		
		super(getSQL(_break), getHiddenColumnNames());
		
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
	
	public Break getBreak() {
		return _break;
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
	
	public void refresh() throws SQLException {
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
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("SlotTable");
		Break b = new Break(12);
		SlotTable table = new SlotTable(b);
		
		// Create frame with table
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
		
		// Test remove
		System.out.println("\nWaiting to remove...");
		Thread.sleep(5000);
		System.out.println("Removing...");
		table.remove(1);
		frame.pack();
		System.out.println("Now has rows: " + table.getRowCount());
	}
}

