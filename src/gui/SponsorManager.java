package gui;
/*
 * SponsorManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import data.Sponsor;
import java.sql.SQLException;


/**
 * Manages sponsors.
 */
public class SponsorManager extends DatabaseTableManager  {
	
	private final SponsorInsertDialog insertDialog;
	private final SponsorUpdateDialog updateDialog;
	
	public SponsorManager(Frame frame) throws SQLException {
		
		super("SELECT * FROM sponsor ORDER BY name");
		
		// Buttons
		addButton("Insert");
		addButton("Update");
		addButton("Delete");
		addButton("Refresh");
		
		// Dialogs
		insertDialog = new SponsorInsertDialog(frame, "New Sponsor");
		insertDialog.pack();
		insertDialog.addActionListener(this);
		updateDialog = new SponsorUpdateDialog(frame, "Update Sponsor");
		updateDialog.pack();
		insertDialog.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Delete")) {
			handleDelete();
		} else if (command.equals("Insert")) {
			handleInsert();
		} else if (command.equals("Update")) {
			handleUpdate();
		} else if (command.equals("Refresh")) {
			refresh();
		}
	}
	
	public String getKey() {
	   return hasSelected() ? (String) getValueAt(getSelectedRow(), 0) : null;
	}
	
	private void handleDelete() {
		
		String key = getKey();
		
		// Confirm
		if (key != null && showConfirmDelete(key)) {
			try {
				Sponsor.delete(key);
				refresh();
				showSuccessfulDelete(key);
			} catch (SQLException e) {
				showUnsuccessfulDelete(key);
			}
		}
	}
	
	
	private void handleInsert() {
		insertDialog.pack();
		insertDialog.setLocationRelativeTo(this);
		insertDialog.setVisible(true);
	}
	
	private void handleUpdate() {
		
		String key = getKey();
		
      if (key == null) {
         GUI.showMessage(this, "Please select a sponsor.");
         return;
      }
		
		try {
			updateDialog.setSponsor(new Sponsor(key));
			updateDialog.pack();
			updateDialog.setLocationRelativeTo(this);
			updateDialog.setVisible(true);
		} catch (SQLException e) {
			GUI.showError(this, "Could not retrieve sponsor!");
		}
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("SponsorManager");
		
		try {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SponsorManager(frame));
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

