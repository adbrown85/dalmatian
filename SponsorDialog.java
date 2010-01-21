/*
 * SponsorDialog.java
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
 * Dialog window for editing a %Sponsor.
 */
public class SponsorDialog extends InputDialog
                           implements ActionListener {
	
	
	/**
	 * Creates a new %SponsorDialog.
	 */
	public SponsorDialog(JFrame frame,
	                     String title) {
		
		// Initialize
		super(frame, title);
		init();
	}
	
	
	/**
	 * Handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Clear")) {
			handleClear();
		} else if (command.equals("Insert")) {
			handleInsert();
		}
	}
	
	
	/**
	 * Makes a sponsor from the input fields and returns it.
	 */
	public Sponsor getSponsor() {
		
		Sponsor sponsor;
		String name, street, city, state, zip, phone;
		
		// Get input
		name = ((JTextField)inputPanel.getInput("Name")).getText();
		street = ((JTextField)inputPanel.getInput("Street")).getText();
		city = ((JTextField)inputPanel.getInput("City")).getText();
		state = ((JComboBox)inputPanel.getInput("State")).getSelectedItem();
		zip = ((JTextField)inputPanel.getInput("Zip")).getText();
		phone = ((JTextField)inputPanel.getInput("Phone")).getText();
		
		// Set fields
		sponsor = new Sponsor();
		sponsor.setName(name);
		sponsor.setStreet(street);
		sponsor.setCity(city);
		sponsor.setState(state);
		sponsor.setZip(zip);
		sponsor.setPhone(phone);
		return sponsor;
	}
	
	
	private void handleCancel() {
		
		inputPanel.clear();
		setVisible(false);
	}
	
	
	private void handleClear() {
		
		inputPanel.clear();
	}
	
	
	private void handleInsert() {
		
		Sponsor sponsor;
		
		try {
			
			// Get sponsor and insert it into database
			sponsor = getSponsor();
			sponsor.insert();
			JOptionPane.showMessageDialog(frame, "Inserted sponsor.");
			fireActionEvent("Refresh");
			setVisible(false);
		}
		catch (SQLException e) {
			System.err.println("[SponsorDialog] Error inserting sponsor.");
			System.err.println("[SponsorDialog] Check missing fields.");
		}
	}
	
	
	private void init() {
		
		// Components
		initInputs();
		initButtons();
	}
	
	
	private void initButtons() {
		
		// Create and add buttons
		buttonPanel.addButton("Insert");
		buttonPanel.addButton("Cancel");
		buttonPanel.addButton("Clear");
	}
	
	
	private void initInputs() {
		
		// Create and add inputs
		inputPanel.addInput("Name", new JTextField(20));
		inputPanel.addInput("Street", new JTextField(20));
		inputPanel.addInput("City", new JTextField(16));
		inputPanel.addInput("State", new JComboBox(State.getCodes()));
		inputPanel.addInput("Zip", new JTextField(5));
		inputPanel.addInput("Phone", new JTextField(10));
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SponsorDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorDialog");
		System.out.println("****************************************");
		System.out.println();
		
		// Create window
		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		dialog = new SponsorDialog(frame, "Sponsor Dialog");
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

