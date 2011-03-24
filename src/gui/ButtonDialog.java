/*
 * ButtonDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import javax.swing.*;


/**
 * Dialog with buttons at the bottom.
 * 
 * <p>Uses a BorderLayout with a ButtonPanel taking up the bottom
 * space.  Calling addButton() will automatically add a JButton with
 * that name to the panel.  The button's action command will be set to
 * its name and the dialog will be registered with the button so that
 * its actionPerformed() method will receive ActionEvents from the
 * button.
 * 
 * <p>Also, ButtonDialog overrides add() so that any components will
 * automatically be added to the center of the BorderLayout.
 */
public class ButtonDialog extends ActionDialog {
	
	protected final ButtonPanel buttonPanel;
	protected final JPanel centerPanel;
	protected final JPanel contentPane;
	
	public ButtonDialog(Frame frame, String title) {
		
		super(frame, title);
		
		// Content pane
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		setContentPane(contentPane);
		
		// Button panel
		buttonPanel = new ButtonPanel(FlowLayout.RIGHT);
		buttonPanel.addActionListener(this);
		contentPane.add(buttonPanel, BorderLayout.PAGE_END);
		
		// Center panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		contentPane.add(centerPanel, BorderLayout.CENTER);
	}
	
	public Component add(Component component) {
		centerPanel.add(component);
		return component;
	}
	
	protected void addButton(String name) {
		buttonPanel.addButton(name);
	}
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame;
		ButtonDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ButtonDialog");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		dialog = new ButtonDialog(frame, "Button Dialog");
		dialog.addButton("Button!");
		dialog.add(new JLabel("First label"));
		dialog.add(new JLabel("Second label"));
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("ButtonDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

