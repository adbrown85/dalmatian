/*
 * SponsorInsertDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;


/**
 * Dialog window for inserting a Sponsor.
 */
public class SponsorInsertDialog extends SponsorDialog {
	
	public SponsorInsertDialog(Frame frame, String title) {
		
		super(frame, title);
		
		addButton("Insert");
		addButton("Cancel");
		addButton("Clear");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Clear")) {
			handleClear();
		} else if (command.equals("Insert")) {
			handleInsert();
		}
	}
	
	/**
	 * Tries to get sponsor and insert it into the database.
	 * 
	 * <p>If doesn't work, user should
	 * <ul>
	 *   <li>check if any fields are empty, and
	 *   <li>check if sponsor is already in system.
	 * </ul>
	 */
	private void handleInsert() {
		try {
			getSponsor().insert();
			fireActionEvent("Refresh");
			GUI.showMessage(this, "Inserted sponsor.");
			setVisible(false);
		} catch (SQLException e) {
			GUI.showError(this, "Could not insert sponsor!");
		}
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Frame");
		String title = "Insert Sponsor";
		SponsorInsertDialog sid = new SponsorInsertDialog(frame, title);
		
		// Create window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		sid.pack();
		sid.setLocationRelativeTo(frame);
		sid.setVisible(true);
	}
}

