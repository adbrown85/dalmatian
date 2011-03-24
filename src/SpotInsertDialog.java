/*
 * SpotInsertDialog.java
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
 * Dialog for adding a Spot.
 */
public class SpotInsertDialog extends SpotDialog {
	
	
	public SpotInsertDialog(Frame frame)
	                        throws SQLException {
		
		super(frame, "Insert Spot");
		
		// Inputs
		addButton("Insert");
		addButton("Cancel");
		addButton("Clear");
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Insert")) {
			handleInsert();
		} else if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Clear")) {
			handleClear();
		}
	}
	
	
	private void handleInsert() {
		
		// Get and insert
		try {
			getSpot().insert();
			fireActionEvent("Refresh");
			GUI.showMessage(this, "Inserted spot.");
			close();
		} catch (SQLException e) {
			String message = "Could not insert spot:\n" + 
			                 " - Check all required fields are filled in\n" + 
			                 " - Check if spot is already in system\n";
			GUI.showError(this, message);
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SpotInsertDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotInsertDialog");
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
			dialog = new SpotInsertDialog(frame);
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
		System.out.println("SpotInsertDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

