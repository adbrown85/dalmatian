/*
 * BreakEditor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


/**
 * Edits breaks.
 */
public class BreakInsertDialog extends BreakDialog
                               implements ActionListener {
	
	public BreakInsertDialog(Frame frame) throws SQLException {
		
		super(frame, "Insert Break");
		
		addButton("Insert");
		addButton("Cancel");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Insert")) {
			handleInsert();
		} else if (command.equals("Cancel")) {
			handleCancel();
		}
	}
	
	private void handleInsert() {
		try {
			
			// Insert break and slots
			slotManager.setBreak(getBreak());
			slotManager.commit();
			
			// Confirm and hide
			fireActionEvent("Refresh");
			JOptionPane.showMessageDialog(this, "Inserted break.");
			setVisible(false);
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Could not insert break.");
		}
	}
	
	public static void main(String[] args) {
		
		JFrame frame;
		BreakInsertDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakInsertDialog");
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
			dialog = new BreakInsertDialog(frame);
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
		System.out.println("BreakInsertDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

