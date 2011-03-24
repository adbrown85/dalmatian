/*
 * ActionDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;



/**
 * Dialog that's good at dealing with action events.
 * 
 * ActionDialog supports listeners with addActionListener() and 
 * fireActionEvent(). Any listeners registered with addActionListener() will 
 * receive an ActionEvent if the class calls fireActionEvent().
 * 
 * Furthermore ActionDialog provides a simple actionPerformed() method for 
 * receiving ActionEvents from other classes.  It simply prints the 
 * ActionEvent's action command.
 * 
 * Also, sometimes it's necessary to pause events when the dialog is changing 
 * itself around to avoid sending unnecessary ActionEvents.  ActionDialog adds 
 * pauseEvents(), restartEvents(), and eventsArePaused() for that reason.
 */
public class ActionDialog extends JDialog
                          implements ActionListener {
	
	private boolean pausedEvents=false;
	protected final Frame frame;
	protected final Vector<ActionListener> listeners;
	
	
	public ActionDialog(Frame frame,
	                    String title) {
		
		super(frame, title, true);
		
		// Initialize
		this.frame = frame;
		listeners = new Vector<ActionListener>();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		if (!eventsArePaused()) {
			System.err.printf("[ActionDialog] Received \"%s\".\n",
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
		
		ActionEvent event;
		
		// Send to each listener
		event = new ActionEvent(this, 0, command);
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
	
	
	public static void main(String[] args) {
		
		JButton button;
		JFrame frame;
		ActionDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ActionDialog");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog with button
		dialog = new ActionDialog(frame, "Button Dialog");
		button = new JButton("Action");
		button.setActionCommand("Action");
		dialog.add(button);
		button.addActionListener(dialog);
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		dialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println(event.getActionCommand());
			}
		});
		dialog.fireActionEvent("Action fired from ActionDialog.");
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ActionDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

