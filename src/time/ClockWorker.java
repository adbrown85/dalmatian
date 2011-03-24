package time;
/*
 * ClockWorker.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.util.ArrayList;
import java.util.List;


/**
 * Utility for notifying ClockListeners.
 */
public class ClockWorker {
	
	private List<ClockListener> alarmListeners;
	private List<ClockListener> secondListeners;
	
	public ClockWorker() {
		alarmListeners = new ArrayList<ClockListener>();
		secondListeners = new ArrayList<ClockListener>();
	}
	
	public void addClockListener(ClockEvent event, ClockListener listener) {
		
		List<ClockListener> listeners = getListeners(event);
		
		if (listeners != null) {
			listeners.add(listener);
		}
	}
	
	
	public void fireClockEvent(ClockEvent event) {
		
		List<ClockListener> listeners = getListeners(event);
		
		if (listeners != null) {
   		for (ClockListener cl : listeners) {
   			cl.clockChanged(event);
   		}
		}
	}
	
	private List<ClockListener> getListeners(ClockEvent event) {
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

