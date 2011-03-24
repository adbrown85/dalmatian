/*
 * DalmatianPlayer.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import gui.BreakQueue;
import gui.ButtonPanel;
import gui.ClockDisplay;
import gui.NextBreakViewer;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import data.Break;
import data.Retriever;


/**
 * 
 */
public class DalmatianPlayer extends Box
                             implements ActionListener {
	
	private final BreakQueue breakQueue;
	private final ButtonPanel buttonPanel;
	private final Clock clock;
	private final ClockDisplay clockDisplay;
	private final NextBreakViewer nextBreakViewer;
	private final Retriever retriever;
	private Break nextBreak;
	private boolean alarmReset;
	
	public DalmatianPlayer() throws SQLException {
		
		super(BoxLayout.PAGE_AXIS);
		
		clock = makeClock();
		clockDisplay = makeClockDisplay(clock);
		breakQueue = makeBreakQueue();
		nextBreak = breakQueue.getNextBreak();
		nextBreakViewer = makeNextBreakViewer(nextBreak);
		buttonPanel = makeButtonPanel(this);
		retriever = new Retriever();
		alarmReset = false;
		
      add(clockDisplay);
      add(nextBreakViewer);
      add(breakQueue);
      add(buttonPanel);
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Refresh")) {
			onRefresh();
		}
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
	private void changeStatus(String status) {
		System.out.println(status);
	}
	
   private static BreakQueue makeBreakQueue() throws SQLException {
      
      BreakQueue bq = new BreakQueue("Break Queue");
      
      bq.setPreferredSize(new Dimension(400, 200));
      return bq;
   }
   
   private static ButtonPanel makeButtonPanel(ActionListener al) {
      
      ButtonPanel bp = new ButtonPanel();
      
      bp.addActionListener(al);
      bp.addButton("Refresh");
      return bp;
   }
	
	private static Clock makeClock() {
		
	   Clock clock = new Clock();
		
		clock.start();
		return clock;
	}
	
	private static ClockDisplay makeClockDisplay(Clock clock) {
	   
	   ClockDisplay cd = new ClockDisplay(clock);
	   int height = (int) (cd.getPreferredSize().getHeight());
	   
	   cd.setBorder(BorderFactory.createTitledBorder("Clock"));
	   cd.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	   return cd;
	}
	
	private static NextBreakViewer makeNextBreakViewer(Break nb)
	                                                   throws SQLException {
	   
	   NextBreakViewer nbv = new NextBreakViewer("Next Break", nb);
	   
	   nbv.setPreferredSize(new Dimension(400, 200));
	   return nbv;
	}
	
   private void loadNextBreak() throws InterruptedException,
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
   
   private void onRefresh() {
      alarmReset = true;
      clock.triggerAlarm();
      System.out.println("After refresh!");
   }
	
	private void playBreak() throws InterruptedException {
		
	   AudioPlayer ap;
		Vector<String> files;
		
		changeStatus("Playing the break!");
		try {
			files = retriever.getFilesForBreak(nextBreak);
			for (String file : files) {
				ap = new AudioPlayer(file);
				ap.start();
				ap.join();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void run() {
		while (true) {
			processBreak();
		}
	}
	
	private void waitForBreak() throws InterruptedException  {
		alarmReset = false;
		clock.setAlarm(nextBreak.getStart());
		changeStatus("Waiting for break.");
		clock.waitForAlarm();
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws SQLException {
		
		DalmatianPlayer dp;
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DalmatianPlayer");
		System.out.println("****************************************");
		System.out.println();
		
		// Create
		dp = new DalmatianPlayer();
		frame = new JFrame("Dalmatian Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(dp);
		frame.pack();
		frame.setVisible(true);
		dp.run();
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DalmatianPlayer");
		System.out.println("****************************************");
		System.out.println();
	}
}

