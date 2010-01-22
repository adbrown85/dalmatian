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
 * Dialog for working with spots.
 */
public class SpotDialog extends InputDialog {
	
	private Spot original=null;
	
	
	public SpotDialog(JFrame frame,
	                  String title)
	                  throws SQLException {
		
		super(frame, title, "Input");
		
		// Inputs
		addInput("Sponsor", new JComboBox(Sponsor.getAllNames()));
		addInput("Title", new JTextField(20));
		addInput("Year", new JComboBox(Year.getAllYears()));
		addInput("Filename", new FilenameInput(24, this));
		addInput("Description", new JTextArea(4,30));
	}
	
	
	public Spot getOriginal() {
		
		return original;
	}
	
	
	public Spot getSpot() {
		
		Spot spot;
		
		// Get inputs
		spot = new Spot();
		spot.setSponsor((String)getItemFrom("Sponsor"));
		spot.setTitle(getTextFrom("Title"));
		spot.setYear((Integer)getItemFrom("Year"));
		spot.setFilename(getTextFrom("Filename"));
		spot.setDescription(getTextFrom("Description"));
		return spot;
	}
	
	
	public void setSpot(Spot spot) {
		
		original = new Spot(spot);
		setItemIn("Sponsor", spot.getSponsor());
		setTextIn("Title", spot.getTitle());
		setItemIn("Year", spot.getYear());
		setTextIn("Filename", spot.getFilename());
		setTextIn("Description", spot.getDescription());
	}
	
	
	protected void handleReset() {
		
		setSpot(original);
	}
	
	
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

