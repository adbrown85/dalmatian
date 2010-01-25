/*
 * SpotManager.java
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
public class SpotManager extends DatabaseTableManager  {
	
	private static final String sql;
	private Retriever retriever;
	private SpotDialog spotInsertDialog;
	private SpotDialog spotUpdateDialog;
	
	
	static {
		sql = "SELECT sponsor,title,year,filename FROM spot " + 
		      "ORDER BY sponsor, title, year";
	}
	
	
	/**
	 * Creates a new %SponsorsManager.
	 */
	public SpotManager(JFrame frame)
	                   throws SQLException {
		
		super(sql);
		
		// Retriever
		retriever = new Retriever();
		
		// Buttons
		addButton("Insert");
		addButton("Update");
		addButton("Delete");
		addButton("Refresh");
		
		// Dialogs
		spotInsertDialog = new SpotInsertDialog(frame);
		spotInsertDialog.addActionListener(this);
		spotInsertDialog.pack();
		spotUpdateDialog = new SpotUpdateDialog(frame);
		spotUpdateDialog.addActionListener(this);
		spotUpdateDialog.pack();
	}
	
	
	/**
	 * Triggered when an action is fired.
	 */
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Handle command
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
	
	
	public int getKey() {
		
		if (hasSelected()) {
			try {
				return retriever.getSpotIdFor(getSponsor(),
				                              getTitle(),
				                              getYear());
			} catch (SQLException e) {
			}
		}
		return -1;
	}
	
	
	public String getDescriptor() {
		
		if (hasSelected())
			return getSponsor() + ", " + getTitle() + ", " + getYear();
		return null;
	}
	
	
	public String getSponsor() {
		
		return (String)getValueAt(getSelectedRow(), 0);
	}
	
	
	public String getTitle() {
		
		return (String)getValueAt(getSelectedRow(), 1);
	}
	
	
	public int getYear() {
		
		return (Integer)getValueAt(getSelectedRow(), 2);
	}
	
	
	private void handleDelete() {
		
		int key;
		String item;
		
		// Confirm
		key = getKey();
		item = getDescriptor();
		if (key != -1 && showConfirmDelete(item)) {
			try {
				Spot.delete(key);
				showSuccessfulDelete(item);
				refresh();
			} catch (SQLException e) {
				showUnsuccessfulDelete(item);
			}
		}
	}
	
	
	private void handleUpdate() {
		
		int key;
		
		// Show sponsor dialog
		try {
			key = getKey();
			if (key == -1) {
				GUI.showMessage(this, "Please select a spot.");
				return;
			}
			spotUpdateDialog.setSpot(new Spot(key));
			spotUpdateDialog.pack();
			spotUpdateDialog.setLocationRelativeTo(this);
			spotUpdateDialog.setVisible(true);
		} catch (SQLException e) {
			GUI.showError(this, "Could not update spot.");
		}
	}
	
	
	/**
	 * Action for the "New" command.
	 */
	private void handleInsert() {
		
		// Show sponsor dialog
		spotInsertDialog.pack();
		spotInsertDialog.setLocationRelativeTo(this);
		spotInsertDialog.setVisible(true);
	}
	
	
	/**
	 * Test for %SponsorManager.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotManager");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		try {
			frame = new JFrame("Spot Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SpotManager(frame));
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

