/*
 * SpotSelector.java
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
public class SpotSelector extends InputDialog {
	
	private static Retriever retriever=null;
	private boolean playingAudio=false;
	private AudioPlayer audioPlayer=null;
	
	
	public SpotSelector(JFrame frame,
	                    String title)
	                    throws SQLException {
		
		super(frame, title, "Input");
		
		// Initialize
		setResizable(false);
		initRetriever();
		initInputs();
		initButtons();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Check pause
		if (eventsArePaused()) {
			return;
		}
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("Sponsor")) {
			handleSponsor();
		} else if (command.equals("Title")) {
			handleTitle();
		} else if (command.equals("Year")) {
			handleYear();
		} else if (command.equals("Play")) {
			handlePlay();
		} else if (command.equals("Stop")) {
			handleStop();
		} else if (command.equals("Refresh")) {
			handleRefresh();
		} else if (command.equals("Select")) {
			handleSelect();
		} else if (command.equals("Cancel")) {
			handleCancel();
		} else if (command.equals("AUDIOPLAYER_PLAYING")) {
			handleAudioPlayerPlaying();
		} else if (command.equals("AUDIOPLAYER_STOPPED")) {
			handleAudioPlayerStopped();
		}
	}
	
	
	public Vector<String> getSponsorsWithSpots()
	                                           throws SQLException {
		
		return retriever.getSponsorsWithSpots();
	}
	
	
	public Spot getSpot()
	                    throws SQLException {
		
		String sponsor, title;
		Integer year;
		
		sponsor = (String)getItemFrom("Sponsor");
		title = (String)getItemFrom("Title");
		year = (Integer)getItemFrom("Year");
		return retriever.getSpotForSponsorTitleYear(sponsor, title, year);
	}
	
	
	public Vector<String> getTitlesForSponsor()
	                                          throws SQLException {
		
		return retriever.getTitlesForSponsor((String)getItemFrom("Sponsor"));
	}
	
	
	public Vector<Integer> getYearsForSponsorTitle()
	                                               throws SQLException {
		
		return retriever.getYearsForSponsorTitle((String)getItemFrom("Sponsor"),
		                                         (String)getItemFrom("Title"));
	}
	
	
	private void handleAudioPlayerPlaying() {
		
		playingAudio = true;
	}
	
	
	private void handleAudioPlayerStopped() {
		
		playingAudio = false;
	}
	
	
	protected void handleCancel() {
		
		pauseEvents();
		clear();
		restartEvents();
		setVisible(false);
	}
	
	
	private void handlePlay() {
		
		if (playingAudio)
			return;
		
		audioPlayer = new AudioPlayer(getTextFrom("Filename"));
		audioPlayer.addActionListener(this);
		audioPlayer.start();
	}
	
	
	private void handleRefresh() {
		
		JComboBox sponsorCombo;
		Vector<String> sponsors;
		
		try {
			pauseEvents();
			sponsorCombo = (JComboBox)getInput("Sponsor");
			sponsorCombo.removeAllItems();
			restartEvents();
			sponsors = getSponsorsWithSpots();
			for (String sponsor : sponsors) {
				sponsorCombo.addItem(sponsor);
			}
		} catch (SQLException e) {
		}
	}
	
	
	private void handleSelect() {
		
		fireActionEvent("Selected");
		setVisible(false);
	}
	
	
	private void handleSponsor() {
		
		JComboBox titleCombo;
		Vector<String> titles;
		
		try {
			pauseEvents();
			titleCombo = (JComboBox)getInput("Title");
			titleCombo.removeAllItems();
			restartEvents();
			titles = getTitlesForSponsor();
			for (String title : titles) {
				titleCombo.addItem(title);
			}
			pack();
		} catch (SQLException e) {
		}
	}
	
	
	private void handleStop() {
		
		if (audioPlayer != null)
			audioPlayer.stopPlaying();
	}
	
	
	private void handleTitle() {
		
		JComboBox yearCombo;
		Vector<Integer> years;
		
		try {
			pauseEvents();
			yearCombo = (JComboBox)getInput("Year");
			yearCombo.removeAllItems();
			restartEvents();
			years = getYearsForSponsorTitle();
			for (Integer year : years) {
				yearCombo.addItem(year);
			}
			pack();
		} catch (SQLException e) {
		}
	}
	
	
	private void handleYear() {
		
		try {
			setFilenameDescription();
		} catch (SQLException e) {
		}
	}
	
	
	private void initButtons() {
		
		String[] names={"Select","Play","Stop","Refresh","Cancel"};
		
		// Add buttons
		for (String name : names) {
			addButton(name);
		}
	}
	
	
	private void initInputs() 
	                        throws SQLException {
		
		Color color=new Color(0.9f, 0.9f, 0.9f);
		JTextArea textArea;
		JTextField textField;
		
		// Add editable inputs
		addInput("Sponsor", new JComboBox(getSponsorsWithSpots()));
		addInput("Title", new JComboBox(getTitlesForSponsor()));
		addInput("Year", new JComboBox(getYearsForSponsorTitle()));
		
		// Add non-editable inputs
		textField = new JTextField(30);
		textField.setEditable(false);
		textField.setBackground(color);
		addInput("Filename", textField);
		textArea = new JTextArea(4, 30);
		textArea.setEditable(false);
		textArea.setBackground(color);
		addInput("Description", textArea);
		setFilenameDescription();
	}
	
	
	private void initRetriever()
	                           throws SQLException {
		
		// Miscellaneous
		if (retriever == null)
			retriever = new Retriever();
	}
	
	
	private void setFilenameDescription()
	                                    throws SQLException {
		
		Spot spot;
		
		// Set filename
		spot = getSpot();
		((JTextField)getInput("Filename")).setText(spot.getFilename());
		((JTextArea)getInput("Description")).setText(spot.getDescription());
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		SpotSelector dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotSelector");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(640, 480));
			frame.pack();
			frame.setVisible(true);
			
			// Create dialog
			dialog = new SpotSelector(frame, "Select Spot");
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
		System.out.println("SpotSelector");
		System.out.println("****************************************");
		System.out.println();
	}
}

