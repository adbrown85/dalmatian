/*
 * Dalmation.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import net.java.games.input.*;


public class Dalmatian implements JoystickListener {
	
	private Joystick joystick;
	private Date nextDate;
	private Vector<File> spots;
	
	/** Plays spots. */
	public void buttonPressed(Component button) throws Exception {
		System.out.println(button.getName() + " was pressed!");
		if (spots != null) {
			Player.play(spots);
		}
	}
	
	public void load(File dir) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		File breaks[] = null;
		
		// Current time
		System.out.println(cal.getTime());
		
		// Get files
		if (dir.isDirectory())
			breaks = dir.listFiles();
		if (breaks != null)
			for (File b : breaks)
				System.out.println(b);
		
		// Parse files
		if (breaks != null)
			for (File b : breaks)
				parse(b);
	}
	
	public void parse(File brk) throws Exception {
		
		Date time, date1, date2;
		Scanner scan = new Scanner(brk);
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		// Get time and dates
		time = tf.parse(scan.next());
		System.out.println("time: " + time);
		date1 = df.parse(scan.next());
		System.out.println("date1: " + date1);
		date2 = df.parse(scan.next());
		System.out.println("date2: " + date2);
		
		// Add spots
		spots = new Vector<File>();
		while (scan.hasNext())
			spots.add(new File("./audio/" + scan.next() + ".wav"));
	}
	
	public void start() throws Exception {
		
		File dir = new File("./breaks");
		
		// Load
		load(dir);
		
		// Start joystick
		joystick = new Joystick();
		joystick.addListener(this);
		joystick.poll(150);
	}
	
	public static void main(String args[]) throws Exception {
		
		Dalmatian d = new Dalmatian();
		d.start();
	}
}

