/*
 * ClockDisplay.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;



/**
 * Displays a clock in several text fields.
 */
public class ClockDisplay extends JLabel
                          implements ClockListener {
	
	private static DateFormat dateFormat=DateFormat.getDateTimeInstance();
	private Clock clock;
	
	
	public ClockDisplay(Clock clock) {
		
		super(dateFormat.format(clock.getTime()));
		
		// Setup clock
		this.clock = clock;
		clock.addClockListener(ClockEvent.SECOND, this);
	}
	
	
	public void clockChanged(ClockEvent event) {
		
		setText(dateFormat.format(clock.getTime()));
	}
	
	
	public static void main(String[] args) {
		
		Clock clock;
		ClockDisplay clockDisplay;
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ClockDisplay");
		System.out.println("****************************************");
		System.out.println();
		
		// Make clock and display
		clock = new Clock();
		clock.start();
		clockDisplay = new ClockDisplay(clock);
		
		// Test
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(clockDisplay);
		frame.pack();
		frame.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ClockDisplay");
		System.out.println("****************************************");
		System.out.println();
	}
}

