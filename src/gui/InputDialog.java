/*
 * InputDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.*;


/**
 * Dialog for taking input from the user.
 * 
 * <p>Basically a ButtonDialog with an InputPanel attached.
 */
public class InputDialog extends ButtonDialog {
	
	protected final InputPanel inputPanel;
	
	public InputDialog(Frame frame,
	                   String frameTitle,
	                   String inputTitle) {
		
		super(frame, frameTitle);
		
		inputPanel = new InputPanel(inputTitle);
		inputPanel.addActionListener(this);
		add(inputPanel);
	}
	
	protected final void addInput(String name,
	                              JComponent input) {
		inputPanel.addInput(name, input);
	}
	
	public void clear() {
		inputPanel.clear();
	}
	
	public void close() {
		clear();
		setVisible(false);
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
   protected void handleCancel() {
      clear();
      setVisible(false);
   }
   
   protected void handleClear() {
      clear();
   }
   
	//------------------------------------------------------------
   // Getters and setters
   //
	
	public final JComponent getInput(String input) {
		return inputPanel.getInput(input);
	}
	
	public final Object getItemFrom(String input) {
		return inputPanel.getItemFrom(input);
	}
	
	public final String getTextFrom(String input) {
		return inputPanel.getTextFrom(input);
	}
	
	public final Timestamp getTimestampFrom(String input) {
		return inputPanel.getTimestampFrom(input);
	}
	
	public final void setItemIn(String input, Object item) {
		inputPanel.setItemIn(input, item);
	}
	
	public final void setTextIn(String input, String text) {
		inputPanel.setTextIn(input, text);
	}
	
	public final void setTimestampIn(String input,
	                                 Timestamp timestamp)
	                                 throws SQLException {
		inputPanel.setTimestampIn(input, timestamp);
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		JFrame frame;
		InputDialog dialog;
		String[] options={"Option 1","Option 2"};
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("InputDialog");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		frame = new JFrame("Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(400, 300));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		dialog = new InputDialog(frame, "Input Dialog", "Input Section");
		dialog.addInput("Combo Box", new JComboBox(options));
		dialog.addButton("Button!");
		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("InputDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

