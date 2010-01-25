/*
 * GUI.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/**
 * GUI utilties.
 */
public class GUI {
	
	
	public static Dimension getInfiniteSize() {
		
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	
	public static JComboBox getComboBoxFor(int start,
	                                       int end) {
		
		JComboBox comboBox;
		
		// Make range
		comboBox = new JComboBox();
		for (int i=start; i<=end; ++i)
			comboBox.addItem(i);
		return comboBox;
	}
	
	
	/**
	 * Shows an error dialog.
	 */
	public static void showError(Component parent,
	                             String message) {
		
		int type;
		String title;
		
		type = JOptionPane.ERROR_MESSAGE;
		title = "Error";
		JOptionPane.showMessageDialog(parent, message, title, type);
	}
	
	
	/**
	 * Shows a message dialog.
	 */
	public static void showMessage(Component parent,
	                               String message) {
		
		JOptionPane.showMessageDialog(parent, message);
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("GUI");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Show error
		GUI.showError(frame, "This is an error!");
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("GUI");
		System.out.println("****************************************");
		System.out.println();
	}
}

