/*
 * SponsorInsertDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.*;




/**
 * Dialog window for inserting a %Sponsor.
 */
public class SponsorInsertDialog extends SponsorDialog {
	
	
	public SponsorInsertDialog(Frame frame,
	                           String title) {
		
		super(frame, title);
		
		// Buttons
		addButton("Insert");
		addButton("Cancel");
		addButton("Clear");
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Clear")) {
			handleClear();
		} else if (command.equals("Insert")) {
			handleInsert();
		}
	}
	
	
	private void handleInsert() {
		
		try {
			
			// Get sponsor and insert it into database
			getSponsor().insert();
			fireActionEvent("Refresh");
			GUI.showMessage(this, "Inserted sponsor.");
			setVisible(false);
		}
		catch (SQLException e) {
			String message = "Could not insert sponsor:\n" +
			                 " - Check if any fields are empty.\n" +
			                 " - Check if sponsor is already in system.\n";
			GUI.showError(this, message);
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SponsorInsertDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorInsertDialog");
		System.out.println("****************************************");
		System.out.println();
		
		// Create window
		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		dialog = new SponsorInsertDialog(frame, "Insert Sponsor");
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorInsertDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

