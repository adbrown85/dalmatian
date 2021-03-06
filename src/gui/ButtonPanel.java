/*
 * ButtonPanel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;


/**
 * Horizontal panel of buttons.
 */
public class ButtonPanel extends JPanel
                         implements ActionListener {
	
	private final List<ActionListener> listeners;
	
	/**
	 * Creates a new panel of buttons.
	 */
	public ButtonPanel() {
		this(FlowLayout.CENTER);
	}
	
	/**
	 * Creates a new panel of buttons.
	 */
	public ButtonPanel(int align) {
		super(new FlowLayout(align));
		listeners = new ArrayList<ActionListener>();
	}
	
	/**
	 * Forwards events to its listeners.
	 */
	public void actionPerformed(ActionEvent event) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Adds a button and sets it up for events.
	 */
	public void addButton(String name) {
		
		JButton button = new JButton(name);
		
		button.addActionListener(this);
		button.setActionCommand(name);
		add(button);
	}
	
	/**
	 * Test for ButtonPanel.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		ButtonPanel buttonPanel;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ButtonPanel");
		System.out.println("****************************************");
		System.out.println();
		
		// Create button panel
		buttonPanel = new ButtonPanel();
		buttonPanel.addButton("Add");
		buttonPanel.addButton("Cancel");
		buttonPanel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				System.out.println(event.getActionCommand());
			}
		});
		
		// Create frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(buttonPanel);
		frame.pack();
		frame.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ButtonPanel");
		System.out.println("****************************************");
		System.out.println();
	}
}

