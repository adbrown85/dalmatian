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
	
	private SponsorDialog sponsorDialog;
	private static final String sql;
	
	
	static {
		sql = "SELECT * FROM sponsor " + 
		      "ORDER BY name";
	}
	
	
	/**
	 * Creates a new %SponsorsManager.
	 */
	public SponsorManager(JFrame frame)
	                      throws SQLException {
		
		super(frame, sql);
		init();
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
		} else if (command.equals("Edit")) {
			handleEdit();
		} else if (command.equals("New")) {
			handleNew();
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
				showSuccessfulDelete(key);
				refresh();
			} catch (SQLException e) {
				showUnsuccessfulDelete(key);
			}
		}
	}
	
	
	private void handleEdit() {
		
		String key;
		
		key = getKey();
		System.out.printf("[SponsorManager] Should edit \"%s\"\n", key);
	}
	
	
	/**
	 * Action for the "New" command.
	 */
	private void handleNew() {
		
		// Show sponsor dialog
		sponsorDialog.setLocationRelativeTo(this);
		sponsorDialog.setVisible(true);
	}
	
	
	private void init() {
		
		initButtons();
		initSponsorDialog();
	}
	
	
	/**
	 * Initializes the buttons.
	 */
	private void initButtons() {
		
		String[] names={"New","Edit","Delete","Refresh"};
		
		// Add buttons to panel
		for (String name : names) {
			buttonPanel.addButton(name);
		}
	}
	
	
	private void initSponsorDialog() {
		
		sponsorDialog = new SponsorDialog(frame, "New Sponsor");
		sponsorDialog.addActionListener(this);
		sponsorDialog.pack();
	}
	
	
	/**
	 * Test for %SponsorManager.
	 */
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

