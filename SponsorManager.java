/*
 * SponsorManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.SQLException;



/**
 * Manages sponsors.
 */
public class SponsorManager extends DatabaseTableManager  {
	
	private SponsorInsertDialog insertDialog;
	private SponsorUpdateDialog updateDialog;
	private static final String sql;
	
	
	static {
		sql = "SELECT * FROM sponsor " + 
		      "ORDER BY name";
	}
	
	
	public SponsorManager(JFrame frame)
	                      throws SQLException {
		
		super(sql);
		
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
		
		String command;
		
		command = event.getActionCommand();
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
		
		if (hasSelected()) {
			return (String)getValueAt(getSelectedRow(), 0);
		} else {
			return null;
		}
	}
	
	
	private void handleDelete() {
		
		String key;
		
		// Confirm
		key = getKey();
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
		
		// Show insert dialog
		insertDialog.pack();
		insertDialog.setLocationRelativeTo(this);
		insertDialog.setVisible(true);
	}
	
	
	private void handleUpdate() {
		
		String key;
		
		// Show update dialog
		try {
			key = getKey();
			if (key == null) {
				GUI.showMessage(this, "Please select a sponsor.");
				return;
			}
			updateDialog.setSponsor(new Sponsor(key));
			updateDialog.pack();
			updateDialog.setLocationRelativeTo(this);
			updateDialog.setVisible(true);
		} catch (SQLException e) {
			GUI.showError(this, "Could not retrieve sponsor!");
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorManager");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		try {
			frame = new JFrame("SponsorManager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SponsorManager(frame));
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

