/*
 * SpotDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import data.Sponsor;
import data.Spot;
import data.Year;


/**
 * Dialog for working with spots.
 */
public class SpotDialog extends InputDialog {
	
	private Spot original=null;
	
	public SpotDialog(Frame frame,
	                  String title)
	                  throws SQLException {
		
		super(frame, title, "Input");
		
		addInput("Sponsor", new JComboBox(Sponsor.getAllNames()));
		addInput("Title", new JTextField(20));
		addInput("Year", new JComboBox(Year.getAllYears()));
		addInput("Filename", new FilenameInput(24, this));
		addInput("Description", new JTextArea(4,30));
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
   protected void handleReset() {
      setSpot(original);
   }
	
   //------------------------------------------------------------
   // Getters and setters
   //
   
	public Spot getOriginal() {
		return original;
	}
	
	public Spot getSpot() {
		
		Spot spot = new Spot();
		
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
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("SpotDialog Frame");
		SpotDialog sd = new SpotDialog(frame, "New Spot");
		
		// Create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		sd.pack();
		sd.setLocationRelativeTo(frame);
		sd.setVisible(true);
	}
}

