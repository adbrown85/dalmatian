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
import java.sql.SQLException;
import java.util.TreeMap;
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
	
	Sponsor sponsor;
	JPanel contentPane;
	JPanel buttonPanel, inputPanel;
	TreeMap<String,JTextField> textFields;
	Vector<ActionListener> listeners;
	
	
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
		listeners = new Vector<ActionListener>();
		
		// Add components
		initInputPanel();
		initButtonPanel();
		
		// Pack
		setResizable(false);
		pack();
	}
	
	
	/**
	 * Handles events.
	 */
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("Cancel")) {
			reset();
			setVisible(false);
		} else if (command.equals("Insert")) {
			try {
				insertSponsor();
				System.out.printf("[SponsorDialog] Inserted \"%s\".\n",
				                  sponsor.getName());
				fireActionEvent("Refresh");
				setVisible(false);
			} catch (SQLException e) {
				System.err.println("[SponsorDialog] Error inserting sponsor.");
				System.err.println("[SponsorDialog] Check missing fields.");
			}
		}
	}
	
	
	public void addActionListener(ActionListener listener) {
		
		listeners.add(listener);
	}
	
	
	/**
	 * Signals a change was made to all listeners registered.
	 */
	private void fireActionEvent(String command) {
		
		ActionEvent event;
		
		// Send to each listener
		event = new ActionEvent(this, 0, command);
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	
	/**
	 * Initializes the button panel.
	 */
	private void initButtonPanel() {
		
		JButton button;
		String[] buttonNames={"Insert", "Cancel"};
		
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
	private void initInputPanel() {
		
		Vector<String> textFieldNames;
		
		// Create and add to content pane
		inputPanel = new JPanel(new GridBagLayout());
		contentPane.add(inputPanel);
		
		// Add labels and text fields
		textFieldNames = Sponsor.getFieldNames();
		textFields = new TreeMap<String,JTextField>();
		for (int i=0; i<textFieldNames.size(); ++i) {
			initInputPanelLabel(i, textFieldNames.get(i));
			initInputPanelTextField(i, textFieldNames.get(i));
		}
	}
	
	
	/**
	 * Adds a label to the input panel.
	 */
	private void initInputPanelLabel(int row,
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
	private void initInputPanelTextField(int row,
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
		inputPanel.add(textField, gbc);
		textFields.put(textFieldName, textField);
	}
	
	
	/**
	 * Inserts the sponsor into the database.
	 */
	private void insertSponsor()
	                           throws SQLException {
		
		// Create sponsor
		sponsor = new Sponsor();
		sponsor.setName(textFields.get("Name").getText());
		sponsor.setStreet(textFields.get("Street").getText());
		sponsor.setCity(textFields.get("City").getText());
		sponsor.setState(textFields.get("State").getText());
		sponsor.setZip(textFields.get("Zip").getText());
		sponsor.setPhone(textFields.get("Phone").getText());
		sponsor.insert();
	}
	
	
	/**
	 * Resets all the fields.
	 */
	private void reset() {
		
		for (JTextField textField : textFields.values())
			textField.setText(null);
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

