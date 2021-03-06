/*
 * BreakQueue.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.sql.*;
import javax.swing.*;
import data.Break;
import data.DatabaseTable;


/**
 * Component for viewing and retrieving upcoming breaks.
 */
public class BreakQueue extends Box {
	
	private static final String sql;
	private static final String[] hiddenColumns;
	private DatabaseTable table;
	
	static {
		sql = "SELECT id, " + 
		              "date_format(start,'%a') AS day, " + 
		              "date_format(start,'%b %d %Y') AS date, " + 
		              "date_format(start,'%r') AS time " + 
		       "FROM break " + 
		       "WHERE start >= NOW() " + 
		       "ORDER BY start " + 
		       "LIMIT 5";
		hiddenColumns = new String[1];
		hiddenColumns[0] = "id";
	}
	
	public BreakQueue() throws SQLException {
		this(null);
	}
	
	public BreakQueue(String title) throws SQLException {
	   
		super(BoxLayout.PAGE_AXIS);
		
      // Table
      table = new DatabaseTable(sql, hiddenColumns);
      add(GUI.getScrollPaneFor(table, 2));
      
      // Titled border
      if (title != null) {
         setBorder(BorderFactory.createTitledBorder(title));
      }
	}
	
	public Break getNextBreak() throws SQLException {
		return new Break((Integer)table.getValueAt(0, "id"));
	}
	
	public void refresh() throws SQLException {
		table.refresh();
	}
	
	public static void main(String[] args) {
		
		BreakQueue queue;
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakQueue");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Show queue in frame
			frame = new JFrame("BreakQueue Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(queue = new BreakQueue("Break Queue"));
			frame.pack();
			frame.setVisible(true);
			
			// Get next break
			System.out.println(queue.getNextBreak());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakQueue");
		System.out.println("****************************************");
		System.out.println();
	}
}

