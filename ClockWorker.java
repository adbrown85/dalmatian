/*
 * ClockWorker.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.util.Calendar;
import java.util.Vector;



/**
 * Utility for notifing ClockListeners.
 */
public class ClockWorker {
	
	private Vector<ClockListener> alarmListeners, secondListeners;
	
	
	public ClockWorker() {
		
		// Create groups
		alarmListeners = new Vector<ClockListener>();
		secondListeners = new Vector<ClockListener>();
	}
	
	
	public void addClockListener(ClockEvent event,
	                             ClockListener listener) {
		
		Vector<ClockListener> listeners;
		
		// Add listener to correct group
		listeners = getListeners(event);
		if (listeners != null) {
			listeners.add(listener);
		}
	}
	
	
	public void fireClockEvent(ClockEvent event) {
		
		Vector<ClockListener> listeners;
		
		// Fire to listeners of correct type
		listeners = getListeners(event);
		if (listeners == null)
			return;
		for (ClockListener listener : listeners) {
			listener.clockChanged(event);
		}
	}
	
	
	private Vector<ClockListener> getListeners(ClockEvent event) {
		
		switch (event) {
			case ALARM :
				return alarmListeners;
			case SECOND :
				return secondListeners;
			default :
				return null;
		}
	}
}

