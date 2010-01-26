/*
 * NextBreakViewer.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;



/**
 * Component for viewing an upcoming break.
 */
public class NextBreakViewer extends Box {
	
	private SlotTable table;
	
	
	public NextBreakViewer()
	                       throws SQLException {
		
		super(BoxLayout.PAGE_AXIS);
		init(null, new Break());
	}
	
	
	public NextBreakViewer(String title)
	                       throws SQLException {
		
		super(BoxLayout.PAGE_AXIS);
		init(title, new Break());
	}
	
	
	public NextBreakViewer(String title,
	                       Break nextBreak)
	                       throws SQLException {
		
		super(BoxLayout.PAGE_AXIS);
		init(title, nextBreak);
	}
	
	
	private void init(String title,
	                  Break nextBreak)
	                  throws SQLException {
		
		// Table
		table = new SlotTable(nextBreak);
		add(GUI.getScrollPaneFor(table, 2));
		
		// Titled border
		if (title != null) {
			setBorder(BorderFactory.createTitledBorder(title));
		}
	}
	
	
	public void refresh()
	                    throws SQLException {
		
		table.refresh();
	}
	
	
	public void setBreak(Break _break) {
		
		table.setBreak(_break);
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("NextBreakViewer");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Show in frame
			frame = new JFrame("NextBreakViewer Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(new NextBreakViewer("Next Break", new Break(12)));
			frame.pack();
			frame.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("NextBreakViewer");
		System.out.println("****************************************");
		System.out.println();
	}
}

