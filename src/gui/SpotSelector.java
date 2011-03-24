/*
 * SpotSelector.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import audio.AudioPlayer;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import data.Retriever;
import data.Spot;


/**
 * Dialog for adding a Spot.
 */
public class SpotSelector extends InputDialog {
	
	private static final Retriever retriever;
	
	private boolean playingAudio=false;
	private AudioPlayer audioPlayer=null;
	
	static {
	   retriever = new Retriever();
	}
	
	public SpotSelector(Frame frame, String title) throws SQLException {
		
		super(frame, title, "Input");
		
		initInputs();
		initButtons();
		
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		// Check pause
		if (eventsArePaused()) {
			return;
		}
		
		// Handle command
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
	
	//------------------------------------------------------------
   // Helpers
   //
	
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
		   ;
		}
	}
	
	private void initButtons() {
		
		String[] names = { "Select", "Play", "Stop", "Refresh", "Cancel" };
		
		for (String name : names) {
			addButton(name);
		}
	}
	
	private void initInputs() throws SQLException {
		
		Color color = new Color(0.9f, 0.9f, 0.9f);
		JTextArea textArea = new JTextArea(4, 30);
		JTextField textField = new JTextField(30);
		
		// Add editable inputs
		addInput("Sponsor", new JComboBox(getSponsorsWithSpots()));
		addInput("Title", new JComboBox(getTitlesForSponsor()));
		addInput("Year", new JComboBox(getYearsForSponsorTitle()));
		
		// Add non-editable inputs
		textField.setEditable(false);
		textField.setBackground(color);
		addInput("Filename", textField);
		textArea.setEditable(false);
		textArea.setBackground(color);
		addInput("Description", textArea);
		setFilenameDescription();
	}
	
	//------------------------------------------------------------
   // Getters and setters
   //
	
   public Vector<String> getSponsorsWithSpots() throws SQLException {
      return retriever.getSponsorsWithSpots();
   }
   
   public Spot getSpot() throws SQLException {
      
      String sponsor = getSponsorItem();
      String title = getTitleItem();
      Integer year = getYearItem();
      
      return retriever.getSpotForSponsorTitleYear(sponsor, title, year);
   }
   
   public Vector<String> getTitlesForSponsor() throws SQLException {
      return retriever.getTitlesForSponsor(getSponsorItem());
   }
   
   public Vector<Integer> getYearsForSponsorTitle() throws SQLException {
      
      String sponsor = getSponsorItem();
      String title = getTitleItem();
      
      return retriever.getYearsForSponsorTitle(sponsor, title);
   }
   
   private String getSponsorItem() {
      return (String) getItemFrom("Sponsor");
   }
   
   private String getTitleItem() {
      return (String) getItemFrom("Title");
   }
   
   private Integer getYearItem() {
      return (Integer) getItemFrom("Year");
   }
   
	private void setFilenameDescription() throws SQLException {
		
		Spot spot = getSpot();
		
		// Set filename
		((JTextField) getInput("Filename")).setText(spot.getFilename());
		((JTextArea) getInput("Description")).setText(spot.getDescription());
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame = new JFrame("Frame");
		SpotSelector ss = new SpotSelector(frame, "Select Spot");
		
		// Create frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		ss.pack();
		ss.setLocationRelativeTo(frame);
		ss.setVisible(true);
	}
}

