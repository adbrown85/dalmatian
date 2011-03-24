/*
 * ClockListener.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package util;


/**
 * Interface for an object that wants to receive Clock events.
 */
public interface ClockListener {
	
	public void clockChanged(ClockEvent event);
}

