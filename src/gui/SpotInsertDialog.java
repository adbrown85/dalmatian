package gui;
/*
 * SpotInsertDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;


/**
 * Dialog for adding a Spot.
 */
public class SpotInsertDialog extends SpotDialog {
	
	public SpotInsertDialog(Frame frame) throws SQLException {
		
		super(frame, "Insert Spot");
		
		addButton("Insert");
		addButton("Cancel");
		addButton("Clear");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Insert")) {
			handleInsert();
		} else if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Clear")) {
			handleClear();
		}
	}
	
	private void handleInsert() {
		try {
			getSpot().insert();
			fireActionEvent("Refresh");
			GUI.showMessage(this, "Inserted spot.");
			close();
		} catch (SQLException e) {
			GUI.showError(this, "Could not insert spot!");
		}
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("Frame");
		SpotInsertDialog sid = new SpotInsertDialog(frame);
		
		// Create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		sid.pack();
		sid.setLocationRelativeTo(frame);
		sid.setVisible(true);
	}
}

