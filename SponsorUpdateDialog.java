/*
 * SponsorUpdateDialog.java
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
 * Dialog window for inserting a %Sponsor.
 */
public class SponsorUpdateDialog extends SponsorDialog {
	
	
	public SponsorUpdateDialog(JFrame frame,
	                           String title) {
		
		super(frame, title);
		
		// Buttons
		addButton("Update");
		addButton("Cancel");
		addButton("Reset");
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("Reset")) {
			handleReset();
		} else if (command.equals("Update")) {
			handleUpdate();
		}
	}
	
	
	private void handleUpdate() {
		
		try {
			Sponsor.update(getOriginal(), getSponsor());
			fireActionEvent("Refresh");
			JOptionPane.showMessageDialog(this, "Updated sponsor.");
			setVisible(false);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Could not do update!");
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SponsorUpdateDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorUpdateDialog");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create window
			frame = new JFrame("Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(320, 240));
			frame.pack();
			frame.setVisible(true);
			
			// Create dialog
			dialog = new SponsorUpdateDialog(frame, "Update Sponsor");
			dialog.setSponsor(new Sponsor("First Citizens National Bank"));
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
		System.out.println("SponsorUpdateDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

