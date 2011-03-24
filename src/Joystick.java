/*
 * Joystick.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
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
	 * @throws IOException
	 *     If no joystick can be found.
	 */
	public Joystick()
	                throws IOException {
		
		// Find first joystick
		controller = findJoystick(controllers);
		if (controller == null)
		
		// Initialize
		initController();
		initButtons();
		listeners = new Vector<JoystickListener>();
	}
	
	
	
	public void addListener(JoystickListener l) {
		
		// Add listener
		listeners.add(l);
	}
	
	
	
	private void initButtons() {
		
		Component components[];
		int count=0, index=0;
		
		// Get components of joystick
		components = controller.getComponents();
		
		// Count how many are buttons
		for (Component c : components)
			if (isButton(c))
				count++;
		
		// Build array
		buttons = new Component[count];
		for (Component c : components)
			if (isButton(c))
				buttons[index++] = c;
	}
	
	
	
	/**
	 * Initializes the reference to the controller.
	 * 
	 * @throws IOException
	 *     If no joystick controller can be found.
	 */
	private void initController()
	                            throws IOException {
		
		ControllerEnvironment environment;
		Controller controllers[];
		
		// Get controllers
		environment = ControllerEnvironment.getDefaultEnvironment();
		controllers = environment.getControllers();
		
		// Find first joystick in controllers
		controller = null;
		for (Controller c : controllers)
			if (c.getType() == Controller.Type.STICK)
				controller = c;
		if (controller == null)
			throw new IOException("No joystick found.");
	}
	
	
	
	private static boolean isButton(Component c) {
		
		// Check if component is a button
		return c.getIdentifier() instanceof Component.Identifier.Button;
	}
	
	
	
	/**
	 * 
	 */
	public void poll(int delay)
	                 throws Exception {
		
		// Poll buttons
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

