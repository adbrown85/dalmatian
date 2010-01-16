/*
 * SponsorDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;




/**
 * Dialog window for editing a %Sponsor.
 */
public class SponsorDialog extends JDialog
                           implements ActionListener {
	
	JPanel contentPane;
	JPanel buttonPanel, inputPanel;
	Vector<JTextField> fields;
	
	
	
	/**
	 * Creates a new %SponsorDialog.
	 */
	public SponsorDialog(JFrame frame,
	                     String title) {
		
		// Initialize
		super(frame, title);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		setContentPane(contentPane);
		
		// Add components
		addInputPanel();
		addButtonPanel();
		
		// Pack
		setResizable(false);
		pack();
	}
	
	
	
	/**
	 * Handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Get command
		command = event.getActionCommand();
		
		// Cancel
		if (command.equals("Cancel")) {
			reset();
			// setVisible(false);
		}
	}
	
	
	
	/**
	 * Initializes the button panel.
	 */
	private void addButtonPanel() {
		
		JButton button;
		String[] buttonNames={"Add", "Cancel"};
		
		// Create and add to content pane
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel);
		
		// Add buttons
		for (int i=0; i<buttonNames.length; ++i) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.addActionListener(this);
			buttonPanel.add(button);
		}
	}
	
	
	
	/**
	 * Initializes the panel with all the input fields.
	 */
	private void addInputPanel() {
		
		JTextField field;
		String fieldNames[]={"Name", "Street", "City", "State", "Zip"};
		
		// Create and add to content pane
		inputPanel = new JPanel(new GridBagLayout());
		contentPane.add(inputPanel);
		
		// Add labels and text fields
		fields = new Vector<JTextField>();
		for (int i=0; i<fieldNames.length; ++i) {
			addInputPanelLabel(i, fieldNames[i]);
			addInputPanelTextField(i, fieldNames[i]);
		}
	}
	
	
	
	/**
	 * Adds a label to the input panel.
	 */
	private void addInputPanelLabel(int row,
	                                String labelName) {
		
		GridBagConstraints gbc;
		JLabel label;
		Insets insets;
		
		// Specify constraints
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(2, 4, 2, 4);
		
		// Make label and add
		label = new JLabel(labelName);
		inputPanel.add(label, gbc);
	}
	
	
	
	/**
	 * Adds a text field to the input panel.
	 */
	private void addInputPanelTextField(int row,
	                                    String textFieldName) {
		
		GridBagConstraints gbc;
		JTextField textField;
		Insets insets;
		
		// Specify constraints
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = row;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(2, 4, 2, 4);
		
		// Make text field and add
		textField = new JTextField(20);
		fields.add(textField);
		inputPanel.add(textField, gbc);
	}
	
	
	
	/**
	 * Resets all the fields.
	 */
	private void reset() {
		
		for (int i=0; i<fields.size(); ++i) {
			fields.get(i).setText(null);
		}
	}
	
	
	
	/**
	 * Test for %SponsorDialog.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		SponsorDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorDialog");
		System.out.println("****************************************");
		System.out.println();
		
		// Create window
		frame = new JFrame("SponsorDialog Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(320, 240));
		frame.pack();
		frame.setVisible(true);
		
		// Create dialog
		dialog = new SponsorDialog(frame, "SponsorDialog Dialog");
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

