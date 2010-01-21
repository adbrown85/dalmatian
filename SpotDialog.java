/*
 * SpotDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.*;



/**
 * Dialog for adding a Spot.
 */
public class SpotDialog extends InputDialog {
	
	
	/**
	 * Creates a new %SpotDialog.
	 */
	public SpotDialog(JFrame frame,
	                  String title)
	                  throws SQLException {
		
		// Initialize
		super(frame, title);
		init();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("Add")) {
			handleAdd();
		} else if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Reset")) {
			handleReset();
		}
	}
	
	
	private void handleAdd() {
		
		int type;
		Spot spot;
		String message, title;
		
		// Get and insert
		try {
			spot = getSpot();
			spot.insert();
			message = "Inserted spot.";
			JOptionPane.showMessageDialog(this, message);
			clear();
			setVisible(false);
		} catch (SQLException e) {
			System.err.println("[SpotDialog] " + e.getMessage());
			title = "Error";
			type = JOptionPane.ERROR_MESSAGE;
			message = "Could not insert spot.\n\n" + e.getMessage();
			JOptionPane.showMessageDialog(this, message, title, type);
		}
	}
	
	
	private void handleCancel() {
		
		clear();
		setVisible(false);
	}
	
	
	private void handleReset() {
		
		clear();
	}
	
	
	public Spot getSpot() {
		
		Spot spot;
		
		// Get inputs
		spot = new Spot();
		spot.setSponsor((String)getItemFrom("Sponsor"));
		spot.setTitle(getTextFrom("Title"));
		spot.setYear((Integer)getItemFrom("Year"));
		spot.setFilename(getFilenameFrom("Filename"));
		spot.setDescription(getTextFrom("Description"));
		return spot;
	}
	
	
	private void init()
	                  throws SQLException {
		
		// Miscellaneous
		setResizable(false);
		
		// Panels
		initInputs();
		initButtons();
	}
	
	
	private void initButtons() {
		
		String[] names={"Add","Cancel","Reset"};
		
		// Add buttons
		for (String name : names) {
			buttonPanel.addButton(name);
		}
	}
	
	
	private void initInputs() 
	                        throws SQLException {
		
		inputPanel.addInput("Sponsor", new JComboBox(Sponsor.getAllNames()));
		inputPanel.addInput("Title", new JTextField(20));
		inputPanel.addInput("Year", new JComboBox(Year.getAllYears()));
		inputPanel.addInput("Filename", new FilenameInput(24, this));
		inputPanel.addInput("Description", new JTextArea(4,30));
	}
	
	
	/**
	 * Test for %SpotDialog.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		SpotDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotDialog");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("SpotDialog Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(640, 480));
			frame.pack();
			frame.setVisible(true);
			
			// Create dialog
			dialog = new SpotDialog(frame, "New Spot");
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
		System.out.println("SpotDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

