/*
 * SponsorDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;




/**
 * Dialog window with inputs for a Sponsor.
 * 
 * Can get and set with getSponsor() and setSponsor().  Also has basic 
 * implementations of handleCancel(), handleClear(), and handleReset().
 */
public class SponsorDialog extends InputDialog {
	
	private Sponsor original=null;
	
	
	public SponsorDialog(Frame frame,
	                     String title) {
		
		super(frame, title, "Input");
		
		// Inputs
		addInput("Name", new JTextField(20));
		addInput("Street", new JTextField(20));
		addInput("City", new JTextField(16));
		addInput("State", new JComboBox(State.getCodes()));
		addInput("Zip", new JTextField(5));
		addInput("Phone", new JTextField(10));
	}
	
	
	public void clear() {
		
		super.clear();
		getInput("Name").requestFocus();
	}
	
	
	public Sponsor getOriginal() {
		
		return original;
	}
	
	
	public Sponsor getSponsor() {
		
		Sponsor sponsor;
		
		sponsor = new Sponsor();
		sponsor.setName(getTextFrom("Name"));
		sponsor.setStreet(getTextFrom("Street"));
		sponsor.setCity(getTextFrom("City"));
		sponsor.setState((String)getItemFrom("State"));
		sponsor.setZip(getTextFrom("Zip"));
		sponsor.setPhone(getTextFrom("Phone"));
		return sponsor;
	}
	
	
	protected void handleClear() {
		
		original = null;
		clear();
	}
	
	
	protected void handleReset() {
		
		setSponsor(original);
	}
	
	
	public void setSponsor(Sponsor sponsor) {
		
		original = new Sponsor(sponsor);
		setTextIn("Name", sponsor.getName());
		setTextIn("Street", sponsor.getStreet());
		setTextIn("City", sponsor.getCity());
		setItemIn("State", sponsor.getState());
		setTextIn("Zip", sponsor.getZip());
		setTextIn("Phone", sponsor.getPhone());
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

