/*
 * ActionDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;


/**
 * Dialog that's good at dealing with action events.
 * 
 * <p>ActionDialog supports listeners with addActionListener() and
 * fireActionEvent(). Any listeners registered with addActionListener()
 * will receive an ActionEvent if the class calls fireActionEvent().
 * 
 * <p>Furthermore ActionDialog provides a simple actionPerformed()
 * method for receiving ActionEvents from other classes.  It simply
 * prints the ActionEvent's action command.
 * 
 * <p>Also, sometimes it's necessary to pause events when the dialog is
 * changing itself around to avoid sending unnecessary ActionEvents.
 * ActionDialog adds pauseEvents(), restartEvents(), and 
 * eventsArePaused() for that reason.
 */
public class ActionDialog extends JDialog implements ActionListener {
	
	protected final Frame frame;
	protected final Vector<ActionListener> listeners;
	private boolean pausedEvents;
	
	public ActionDialog(Frame frame, String title) {
		
		super(frame, title, true);
		
		this.pausedEvents = false;
		this.frame = frame;
		this.listeners = new Vector<ActionListener>();
	}
	
	public void actionPerformed(ActionEvent event) {
		if (!eventsArePaused()) {
			System.err.printf(
			      "[ActionDialog] Received \"%s\".\n",
			      event.getActionCommand());
		}
	}
	
	public final void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	protected boolean eventsArePaused() {
		return pausedEvents;
	}
	
	protected final void fireActionEvent(String command) {
		
		ActionEvent event = new ActionEvent(this, 0, command);
		
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	protected void pauseEvents() {
		pausedEvents = true;
	}
	
	protected void restartEvents() {
		pausedEvents = false;
	}
}
