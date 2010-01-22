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
	private SpotDialog spotDialog;
	
	
	static {
		sql = "SELECT sponsor,title,year,filename FROM spot " + 
		      "ORDER BY sponsor, title, year";
	}
	
	
	/**
	 * Creates a new %SponsorsManager.
	 */
	public SpotManager(JFrame frame)
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
	
	
	private void handleEdit() {
		
		int key;
		
		key = getKey();
		System.out.printf("[SpotManager] Should edit \"%s\"\n", key);
	}
	
	
	/**
	 * Action for the "New" command.
	 */
	private void handleNew() {
		
		// Show sponsor dialog
		spotDialog.setLocationRelativeTo(this);
		spotDialog.setVisible(true);
	}
	
	
	private void init()
	                  throws SQLException {
		
		retriever = new Retriever();
		initButtons();
		initSpotDialog();
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
	
	
	private void initSpotDialog()
	                            throws SQLException {
		
		spotDialog = new SpotDialog(frame, "New Spot");
		spotDialog.addActionListener(this);
		spotDialog.pack();
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

