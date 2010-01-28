/*
 * Clock.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;



/**
 * Thread that keeps track of the time and can notify others of changes.
 */
public class Clock extends Thread {
	
	private static final int PRECISION=200;
	private Calendar alarm, time;
	private int lastSecond=-1, second;
	private ClockWorker worker;
	
	
	public Clock() {
		
		resetAlarm();
		worker = new ClockWorker();
		time = Calendar.getInstance();
	}
	
	
	public void addClockListener(ClockEvent event,
	                             ClockListener listener) {
		
		worker.addClockListener(event, listener);
	}
	
	
	private synchronized void checkAlarm() {
		
		if (alarm == null)
			return;
		
		time.clear(Calendar.MILLISECOND);
		if (time.equals(alarm)) {
			resetAlarm();
			worker.fireClockEvent(ClockEvent.ALARM);
		}
	}
	
	
	public Date getTime() {
		
		return time.getTime();
	}
	
	
	public void run() {
		
		try {
			while (true) {
				time = Calendar.getInstance();
				second = time.get(Calendar.SECOND);
				if (second != lastSecond) {
					worker.fireClockEvent(ClockEvent.SECOND);
					checkAlarm();
				}
				Thread.sleep(PRECISION);
				lastSecond = second;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private synchronized void resetAlarm() {
		
		alarm = null;
	}
	
	
	public synchronized boolean setAlarm(Timestamp timestamp) {
		
		// Make sure it's not already set
		if (alarm != null)
			return false;
		
		// Copy timestamp to alarm in seconds precision
		alarm = Calendar.getInstance();
		alarm.setTimeInMillis(timestamp.getTime());
		alarm.clear(Calendar.MILLISECOND);
		return true;
	}
	
	
	public static void main(String[] args) {
		
		final Clock clock;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Clock");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		clock = new Clock();
		clock.addClockListener(ClockEvent.SECOND, new ClockListener() {
			public void clockChanged(ClockEvent event) {
				System.out.printf("%tT\n", clock.getTime());
			}
		});
		clock.addClockListener(ClockEvent.ALARM, new ClockListener() {
			public void clockChanged(ClockEvent event) {
				System.out.println("Alarm!");
			}
		});
		clock.setAlarm(Timestamp.valueOf("2010-01-27 17:50:00"));
		clock.start();
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Clock");
		System.out.println("****************************************");
		System.out.println();
	}
}

