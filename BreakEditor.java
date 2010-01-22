/*
 * BreakEditor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.TreeMap;
import javax.swing.*;



/**
 * Edits breaks.
 */
public class BreakEditor extends InputDialog
                         implements ActionListener {
	
	
	public BreakEditor(JFrame frame,
	                   String title)
	                   throws SQLException {
		
		super(frame, title, "Date and Time");
		
		// Buttons
		addButton("Update");
		addButton("Cancel");
		
		// Inputs
		addInput("Start", new TimestampEditor(null));
		addInput("End", new TimestampEditor(null));
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Update")) {
			handleUpdate();
		} else if (command.equals("Cancel")) {
			handleCancel();
		}
	}
	
	
	private void handleCancel() {
		
		setVisible(false);
	}
	
	
	private void handleUpdate() {
		
		Break _break;
		String message;
		
		try {
			
			// Insert break
			_break = new Break();
			_break.setStart(getTimestampFrom("Start"));
			_break.setEnd(getTimestampFrom("End"));
			_break.insert();
			
			// Confirm and hide
			fireActionEvent("Refresh");
			JOptionPane.showMessageDialog(frame, "Inserted break.");
			setVisible(false);
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Could not insert break.");
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		BreakEditor dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakEditor");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Make frame
			frame = new JFrame("Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(400, 300));
			frame.pack();
			frame.setVisible(true);
			
			// Make dialog
			dialog = new BreakEditor(frame, "Break Editor");
			dialog.pack();
			dialog.setLocationRelativeTo(frame);
			dialog.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakEditor");
		System.out.println("****************************************");
		System.out.println();
	}
}

