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
public class SponsorDialog extends InputDialog {
	
	
	public SponsorDialog(JFrame frame,
	                     String title) {
		
		// Initialize
		super(frame, title, "Input");
		initInputs();
		initButtons();
	}
	
	
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
		
		// Create new sponsor
		sponsor = new Sponsor();
		sponsor.setName(getTextFrom("Name"));
		sponsor.setStreet(getTextFrom("Street"));
		sponsor.setCity(getTextFrom("City"));
		sponsor.setState((String)getItemFrom("State"));
		sponsor.setZip(getTextFrom("Zip"));
		sponsor.setPhone(getTextFrom("Phone"));
		return sponsor;
	}
	
	
	private void handleCancel() {
		
		clear();
		setVisible(false);
	}
	
	
	private void handleClear() {
		
		clear();
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
	
	
	private void initButtons() {
		
		buttonPanel.addButton("Insert");
		buttonPanel.addButton("Cancel");
		buttonPanel.addButton("Clear");
	}
	
	
	private void initInputs() {
		
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

