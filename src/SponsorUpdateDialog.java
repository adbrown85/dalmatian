/*
 * SponsorUpdateDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;


/**
 * Dialog window for inserting a %Sponsor.
 */
public class SponsorUpdateDialog extends SponsorDialog {
	
	public SponsorUpdateDialog(Frame frame, String title) {
		
		super(frame, title);
		
		addButton("Update");
		addButton("Cancel");
		addButton("Reset");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();;
		
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
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("Frame");
		String title = "Update Sponsor";
		SponsorUpdateDialog sud = new SponsorUpdateDialog(frame, title);
		
		// Create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		sud.setSponsor(new Sponsor("First Citizens National Bank"));
		sud.pack();
		sud.setLocationRelativeTo(frame);
		sud.setVisible(true);
	}
}

