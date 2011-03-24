/*
 * SpotUpdateDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import data.Spot;


/**
 * Dialog for updating a Spot.
 */
public class SpotUpdateDialog extends SpotDialog {
	
	public SpotUpdateDialog(Frame frame) throws SQLException {
		
		super(frame, "Update Spot");
		
		addButton("Update");
		addButton("Cancel");
		addButton("Reset");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Update")) {
			handleUpdate();
		} else if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Reset")) {
			handleReset();
		}
	}
	
	private void handleUpdate() {
		try {
			Spot.update(getOriginal(), getSpot());
			fireActionEvent("Refresh");
			GUI.showMessage(this, "Updated spot.");
			close();
		} catch (SQLException e) {
			GUI.showError(this, "Could not update spot: " + e.getMessage());
		}
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("Frame");
		SpotUpdateDialog sud = new SpotUpdateDialog(frame);
		
		// Create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		sud.setSpot(new Spot(1));
		sud.pack();
		sud.setLocationRelativeTo(frame);
		sud.setVisible(true);
	}
}

