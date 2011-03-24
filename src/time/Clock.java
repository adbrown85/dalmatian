package time;
/*
 * Clock.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 * Thread that keeps track of the time and can notify others of changes.
 */
public class Clock extends Thread {
	
	private static final int PRECISION = 200;
	
	private Calendar alarm, time;
	private ClockWorker worker;
	private int lastSecond=-1, second;
	private Object alarmNotifier;
	
	public Clock() {
		alarm = null;
		worker = new ClockWorker();
		time = Calendar.getInstance();
		alarmNotifier = new Object();
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
			triggerAlarm();
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
	
	public synchronized void triggerAlarm() {
		alarm = null;
		synchronized (alarmNotifier) {
			alarmNotifier.notifyAll();
		}
		worker.fireClockEvent(ClockEvent.ALARM);
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
	
	public void waitForAlarm() throws InterruptedException {
		synchronized (alarmNotifier) {
			alarmNotifier.wait();
		}
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
		clock.setAlarm(Timestamp.valueOf("2010-01-28 02:12:00"));
		clock.start();
		try {
			clock.waitForAlarm();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Clock");
		System.out.println("****************************************");
		System.out.println();
	}
}

