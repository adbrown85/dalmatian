package gui;
/*
 * SponsorDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import Sponsor;
import State;
import java.awt.*;
import javax.swing.*;


/**
 * Dialog window with inputs for a Sponsor.
 * 
 * <p>Can get and set with getSponsor() and setSponsor().  Also has
 * basic implementations of handleCancel(), handleClear(), and 
 * handleReset().
 */
public class SponsorDialog extends InputDialog {
	
	private Sponsor original;
	
	public SponsorDialog(Frame frame, String title) {
		
		super(frame, title, "Input");
		
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
		
		Sponsor sponsor = new Sponsor();
		
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
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("Frame");
		SponsorDialog sd = new SponsorDialog(frame, "Sponsor Dialog");
		
		// Create window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		sd.pack();
		sd.setLocationRelativeTo(frame);
		sd.setVisible(true);
	}
}

