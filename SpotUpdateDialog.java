/*
 * SpotUpdateDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import javax.swing.*;



/**
 * Dialog for updating a Spot.
 */
public class SpotUpdateDialog extends SpotDialog {
	
	
	public SpotUpdateDialog(JFrame frame)
	                        throws SQLException {
		
		super(frame, "Update Spot");
		
		// Inputs
		addButton("Update");
		addButton("Cancel");
		addButton("Reset");
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Update")) {
			handleUpdate();
		} else if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Reset")) {
			handleReset();
		}
	}
	
	
	private void handleUpdate() {
		
		// Get and update
		try {
			Spot.update(getOriginal(), getSpot());
			fireActionEvent("Refresh");
			GUI.showMessage(this, "Updated spot.");
			close();
		} catch (SQLException e) {
			GUI.showError(this, "Could not update spot: " + e.getMessage());
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SpotUpdateDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotUpdateDialog");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(640, 480));
			frame.pack();
			frame.setVisible(true);
			
			// Create dialog
			dialog = new SpotUpdateDialog(frame);
			dialog.setSpot(new Spot(1));
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
		System.out.println("SpotUpdateDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

