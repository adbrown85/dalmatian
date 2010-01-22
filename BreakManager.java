/*
 * BreakManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;



/**
 * Manages the break table.
 */
public class BreakManager extends DatabaseTableManager {
	
	private static final String sql;
	private BreakEditor breakEditor;
	
	
	static {
		sql = "SELECT start,end FROM break ORDER BY start";
	}
	
	
	public BreakManager(JFrame frame)
	                    throws SQLException {
		
		super(frame, sql);
		
		// Buttons
		addButton("New");
		addButton("Delete");
		addButton("Refresh");
		
		// Break editor
		breakEditor = new BreakEditor(frame, "Break Editor");
		breakEditor.pack();
		breakEditor.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("New")) {
			handleNew();
		} else if (command.equals("Refresh")) {
			handleRefresh();
		}
	}
	
	
	private void handleDelete() {
		
		System.out.println("Delete");
	}
	
	
	private void handleNew() {
		
		breakEditor.setLocationRelativeTo(this);
		breakEditor.setVisible(true);
	}
	
	
	private void handleRefresh() {
		
		refresh();
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakManager");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("Break Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new BreakManager(frame));
			frame.pack();
			frame.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

