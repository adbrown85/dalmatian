/*
 * DalmatianPlayer.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;



/**
 * 
 */
public class DalmatianPlayer extends Box
                             implements ActionListener {
	
	boolean alarmReset=false;
	AudioPlayer audioPlayer;
	Break nextBreak;
	BreakQueue breakQueue;
	ButtonPanel buttonPanel;
	Clock clock;
	ClockDisplay clockDisplay;
	NextBreakViewer nextBreakViewer;
	Retriever retriever;
	
	
	DalmatianPlayer() {
		
		super(BoxLayout.PAGE_AXIS);
		
		try {
			
			// Initialize
			initClock();
			initBreakQueue();
			initNextBreak();
			initButtonPanel();
			retriever = new Retriever();
			
			// Add
			add(clockDisplay);
			add(nextBreakViewer);
			add(breakQueue);
			add(buttonPanel);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Refresh")) {
			handleRefresh();
		}
	}
	
	
	private void changeStatus(String status) {
		
		System.out.println(status);
	}
	
	
	private void handleRefresh() {
		
		alarmReset = true;
		clock.triggerAlarm();
		System.out.println("After refresh!");
	}
	
	
	private void initBreakQueue()
	                            throws SQLException {
		
		breakQueue = new BreakQueue("Break Queue");
		breakQueue.setPreferredSize(new Dimension(400, 200));
	}
	
	
	private void initButtonPanel() {
		
		buttonPanel = new ButtonPanel();
		buttonPanel.addActionListener(this);
		buttonPanel.addButton("Refresh");
	}
	
	
	private void initClock() {
		
		int height;
		
		// Clock
		clock = new Clock();
		clock.start();
		
		// Display
		clockDisplay = new ClockDisplay(clock);
		clockDisplay.setBorder(BorderFactory.createTitledBorder("Clock"));
		height = (int)clockDisplay.getPreferredSize().getHeight();
		clockDisplay.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	}
	
	
	private void initNextBreak() 
	                           throws SQLException {
		
		nextBreak = breakQueue.getNextBreak();
		nextBreakViewer = new NextBreakViewer("Next Break", nextBreak);
		nextBreakViewer.setPreferredSize(new Dimension(400, 200));
	}
	
	
	private void loadNextBreak()
	                           throws InterruptedException,
	                                  SQLException {
		
		breakQueue.refresh();
		nextBreak = breakQueue.getNextBreak();
		if (nextBreak != null) {
			nextBreakViewer.setBreak(nextBreak);
			nextBreakViewer.refresh();
		} else {
			Thread.sleep(5000);
		}
	}
	
	
	private void playBreak()
	                       throws InterruptedException {
		
		Vector<String> files;
		
		changeStatus("Playing the break!");
		try {
			files = retriever.getFilesForBreak(nextBreak);
			for (String file : files) {
				audioPlayer = new AudioPlayer(file);
				audioPlayer.start();
				audioPlayer.join();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void processBreak() {
		
		try {
			
			waitForBreak();
			if (!alarmReset) {
				playBreak();
			}
			loadNextBreak();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void run() {
		
		// Run
		while (true) {
			processBreak();
		}
	}
	
	
	private void waitForBreak()
	                          throws InterruptedException  {
		
		alarmReset = false;
		clock.setAlarm(nextBreak.getStart());
		changeStatus("Waiting for break.");
		clock.waitForAlarm();
	}
	
	
	public static void main(String[] args) {
		
		DalmatianPlayer dalmatianPlayer;
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DalmatianPlayer");
		System.out.println("****************************************");
		System.out.println();
		
		// Create
		dalmatianPlayer = new DalmatianPlayer();
		frame = new JFrame("Dalmatian Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(dalmatianPlayer);
		frame.pack();
		frame.setVisible(true);
		dalmatianPlayer.run();
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DalmatianPlayer");
		System.out.println("****************************************");
		System.out.println();
	}
}

