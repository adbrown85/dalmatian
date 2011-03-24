/*
 * Joystick.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package input;

import java.io.IOException;
import java.util.Vector;
import net.java.games.input.*;


public class Joystick {
	
	Component buttons[];
	Controller controller;
	Vector<JoystickListener> listeners;
	
	/**
	 * Creates a reference to a joystick attached to the system.
	 * 
	 * @throws IOException if no joystick can be found
	 */
	public Joystick() throws IOException {
		
		// Find first joystick
		controller = findJoystick(controllers);
		if (controller == null)
		
		// Initialize
		initController();
		initButtons();
		listeners = new Vector<JoystickListener>();
	}
	
	public void addListener(JoystickListener l) {
		listeners.add(l);
	}
	
	private void initButtons() {
		
		Component components[] = controller.getComponents();
		int count = 0;
		int index = 0;
		
		// Count how many are buttons
		for (Component c : components) {
			if (isButton(c)) {
				count++;
			}
		}
		
		// Build array
		buttons = new Component[count];
		for (Component c : components) {
			if (isButton(c)) {
				buttons[index++] = c;
			}
		}
	}
	
	/**
	 * Initializes the reference to the controller.
	 * 
	 * @throws IOException if no joystick controller can be found
	 */
	private void initController() throws IOException {
		
		ControllerEnvironment env = getDefaultControllerEnvironment();
		Controller controllers[] = env.getControllers();
		
		// Find first joystick in controllers
		controller = null;
		for (Controller c : controllers) {
			if (c.getType() == Controller.Type.STICK) {
				controller = c;
			}
		}
		
		// Make sure it was found
		if (controller == null) {
			throw new IOException("No joystick found.");
		}
	}
	
	private static boolean isButton(Component c) {
		return c.getIdentifier() instanceof Component.Identifier.Button;
	}
	
	private static ControllerEnvironment getDefaultControllerEnvironment() {
	   return ControllerEnvironment.getDefaultEnvironment();
	}
	
	public void poll(int delay) throws Exception {
		while (controller.poll()) {
			for (Component b : buttons) {
				if (b.getPollData() == 1.0) {
					for (JoystickListener l : listeners) {
						l.buttonPressed(b);
						break;
					}
				}
			}
			Thread.sleep(delay);
		}
	}
}

