/*
 * JoystickListener.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package input;

import net.java.games.input.*;


interface JoystickListener {
	
	public void buttonPressed(Component button) throws Exception;
}

