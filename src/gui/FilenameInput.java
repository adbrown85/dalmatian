/*
 * FilenameInput.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Text field and button.
 */
public class FilenameInput extends JPanel
                           implements ActionListener {
	
   private static final String BUTTON_NAME="Browse";
   
	private Component dialogParent;
	private JButton button;
	private JFileChooser fileChooser;
	private JTextField textField;
	
	public FilenameInput(int size, Component dialogParent) {
		super(new GridBagLayout());
		init(size, dialogParent, true);
		setMaximumSize(GUI.getInfiniteSize());
	}
	
	public FilenameInput(int size, boolean editable) {
		super(new GridBagLayout());
		init(size, null, editable);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button) {
			onButton();
		}
	}
	
	private void onButton() {
		
		File file;
		int option = fileChooser.showOpenDialog(dialogParent);
		
		// Get file from file chooser
		if (option == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			setText(file.getPath());
		}
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
	private void init(int size, Component dialogParent, boolean editable) {
		
		// Store parent
		this.dialogParent = dialogParent;
		
		// Add text field and button
		initTextField(size);
		if (editable) {
			initButton();
			initFileChooser();
		}
	}
	
	private void initButton() {
		
		GridBagConstraints gbc;
		
		// Setup constraints
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.insets = new Insets(0, 4, 0, 0);
		
		// Add it
		button = new JButton(BUTTON_NAME);
		button.addActionListener(this);
		add(button, gbc);
	}
	
	private void initFileChooser() {
		fileChooser = new JFileChooser();
	}
	
	private void initTextField(int size) {
		
		GridBagConstraints gbc;
		
		// Setup constraints
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		// Add it
		textField = new JTextField(size);
		textField.setMaximumSize(GUI.getInfiniteSize());
		add(textField, gbc);
	}
	
	//------------------------------------------------------------
   // Getters and setters
   //
	
   public String getText() {
      return textField.getText();
   }
	
	public void setText(String text) {
		textField.setText(text);
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("FilenameInput");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		frame = new JFrame("FilenameInput");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new FilenameInput(30, frame));
		frame.pack();
		frame.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("FilenameInput");
		System.out.println("****************************************");
		System.out.println();
	}
}

