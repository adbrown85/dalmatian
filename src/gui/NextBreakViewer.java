package gui;
/*
 * NextBreakViewer.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import Break;
import SlotTable;
import java.sql.*;
import javax.swing.*;


/**
 * Component for viewing an upcoming break.
 */
public class NextBreakViewer extends Box {
	
	private final SlotTable table;
	
	public NextBreakViewer() throws SQLException {
		this(null);
	}
	
	public NextBreakViewer(String title) throws SQLException {
		this(title, new Break());
	}
	
	public NextBreakViewer(String title,
	                       Break nextBreak)
	                       throws SQLException {
		super(BoxLayout.PAGE_AXIS);
		
      // Table
      table = new SlotTable(nextBreak);
      add(GUI.getScrollPaneFor(table, 2));
      
      // Titled border
      if (title != null) {
         setBorder(BorderFactory.createTitledBorder(title));
      }
	}
	
	public void refresh() throws SQLException {
		table.refresh();
	}
	
	public void setBreak(Break b) {
		table.setBreak(b);
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		final JFrame frame = new JFrame("NextBreakViewer Frame");
		final Break b = new Break(12);
		final NextBreakViewer nbv = new NextBreakViewer("Next Break", b);
		
		SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(nbv);
            frame.pack();
            frame.setVisible(true);
         }
		});
	}
}

