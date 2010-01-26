/*
 * GUI.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



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
	
	
	public static JScrollPane getScrollPaneFor(Component component) {
		
		int policy=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		JScrollPane scrollPane;
		
		scrollPane = new JScrollPane(component);
		scrollPane.setMaximumSize(getInfiniteSize());
		scrollPane.setVerticalScrollBarPolicy(policy);
		return scrollPane;
	}
	
	
	protected static void setScrollPaneBorder(JScrollPane scrollPane,
	                                          Border border) {
		
		Border compound, inside;
		
		inside = scrollPane.getBorder();
		compound = BorderFactory.createCompoundBorder(border, inside);
		scrollPane.setBorder(compound);
	}
	
	
	public static boolean showConfirmDelete(Component parent) {
		
		int option, style;
		String message, title;
		
		// Show confirm dialog
		title = "Confirm Delete";
		message = "Are you sure you want to delete this item?";
		style = JOptionPane.YES_NO_OPTION;
		option = JOptionPane.showConfirmDialog(parent, message, title, style);
		return option == JOptionPane.YES_OPTION;
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

