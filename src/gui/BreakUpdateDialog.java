package gui;
/*
 * BreakUpdateDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import Break;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


/**
 * Edits breaks.
 */
public class BreakUpdateDialog extends BreakDialog
                               implements ActionListener {
	
	public BreakUpdateDialog(Frame frame) throws SQLException {
		
		super(frame, "Update Break");
		
		addButton("Cancel");
		addButton("Update");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Update")) {
			handleUpdate();
		} else if (command.equals("Cancel")) {
			handleCancel();
		}
	}
	
	private void handleUpdate() {
		try {
			
			// Insert break and slots
			slotManager.setBreak(getBreak());
			slotManager.commit();
			
			// Confirm and hide
			fireActionEvent("Refresh");
			JOptionPane.showMessageDialog(this, "Updated break.");
			setVisible(false);
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Could not update break.");
		}
	}
	
	public void setBreak(Break _break)
	                     throws SQLException {
		
		super.setBreak(_break);
		
		slotManager.setBreak(_break);
		slotManager.refresh();
	}
	
	public static void main(String[] args) {
		
		JFrame frame;
		BreakUpdateDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakUpdateDialog");
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
			dialog = new BreakUpdateDialog(frame);
			dialog.setBreak(new Break(12));
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
		System.out.println("BreakUpdateDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

