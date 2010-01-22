/*
 * InputDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Vector;
import javax.swing.*;



/**
 * Dialog for taking input from the user.
 * 
 * Basically a ButtonDialog with an InputPanel attached.
 */
public class InputDialog extends ButtonDialog {
	
	private final InputPanel inputPanel;
	
	
	public InputDialog(JFrame frame,
	                   String frameTitle,
	                   String inputTitle) {
		
		super(frame, frameTitle);
		
		// Input panel
		inputPanel = new InputPanel(inputTitle);
		inputPanel.addActionListener(this);
		add(inputPanel, BorderLayout.CENTER);
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
	
	
	protected void handleCancel() {
		
		clear();
		setVisible(false);
	}
	
	
	protected void handleClear() {
		
		clear();
	}
	
	
	public final void setItemIn(String input,
	                            Object item) {
		
		inputPanel.setItemIn(input, item);
	}
	
	
	public final void setTextIn(String input,
	                            String text) {
		
		inputPanel.setTextIn(input, text);
	}
	
	
	public static void main(String[] args) {
		
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

