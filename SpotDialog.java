/*
 * SpotDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.*;



/**
 * Dialog for adding a Spot.
 */
public class SpotDialog extends JDialog
                        implements ActionListener {
	
	JPanel contentPane;
	JPanel buttonPanel, inputPanel;
	TreeMap<String,JComponent> inputs;
	
	
	/**
	 * Creates a new %SpotDialog.
	 */
	public SpotDialog(JFrame frame,
	                  String title)
	                  throws SQLException {
		
		// Initialize
		super(frame, title);
		init();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		// Handle command
		if (command.equals("Add")) {
			handleAdd();
		} else if (command.equals("Browse")) {
			handleBrowse();
		} else if (command.equals("Cancel")) {
			reset();
			setVisible(false);
		} else if (command.equals("Reset")) {
			reset();
		}
	}
	
	
	private void handleAdd() {
		
		Spot spot;
		
		// Get text fields
		spot = new Spot();
		spot.setName(((JTextField)inputs.get("Name")).getText());
		spot.setFilename(((JTextField)inputs.get("Filename")).getText());
		spot.setDescription(((JTextArea)inputs.get("Description")).getText());
		spot.setSponsor((String)((JComboBox)inputs.get("Sponsor")).getSelectedItem());
		spot.setYear((Integer)((JComboBox)inputs.get("Year")).getSelectedItem());
		
		// Try insert
		try {
			spot.insert();
			reset();
			setVisible(false);
		} catch (SQLException e) {
			System.err.println("[SpotDialog] Could not insert new spot.");
		}
	}
	
	
	private void handleBrowse() {
		
		File file;
		int result;
		JFileChooser fileChooser;
		
		// Get filename
		fileChooser = new JFileChooser();
		result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			((JTextField)(inputs.get("Filename"))).setText(file.getPath());
		}
	}
	
	
	private void reset() {
		
		JComponent component;
		
		// Clear text
		((JTextField)inputs.get("Name")).setText(null);
		((JTextField)inputs.get("Filename")).setText(null);
		((JTextArea)inputs.get("Description")).setText(null);
		
		// Reset combo boxes
		((JComboBox)inputs.get("Sponsor")).setSelectedIndex(0);
		((JComboBox)inputs.get("Year")).setSelectedIndex(0);
	}
	
	
	private void init()
	                  throws SQLException {
		
		// Initialize characteristics
		setResizable(false);
		
		// Initialize panels
		initContentPane();
		initInputPanel();
		initButtonPanel();
	}
	
	
	private void initButtonPanel() {
		
		JButton button;
		String[] names={"Add","Cancel","Reset"};
		
		// Create and add panel
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel);
		
		// Add buttons
		for (String name : names) {
			button = new JButton(name);
			button.addActionListener(this);
			button.setActionCommand(name);
			buttonPanel.add(button);
		}
	}
	
	
	private void initContentPane() {
		
		// Change content pane layout
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		setContentPane(contentPane);
	}
	
	
	private void initInput(JComponent component,
	                       int x,
	                       int y) {
		
		GridBagConstraints gbc;
		
		// Setup constraints and add component
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.insets = new Insets(2, 4, 2, 4);
		inputPanel.add(component, gbc);
	}
	
	
	private void initInputForSponsor()
	                                 throws SQLException {
		
		JComboBox comboBox;
		
		// Add label and combo box
		initInput(new JLabel("Sponsor"), 0, 0);
		comboBox = new JComboBox(Sponsor.retrieveAllNames());
		initInput(comboBox, 1, 0);
		inputs.put("Sponsor", comboBox);
	}
	
	
	private void initInputForName() {
		
		JTextField textField;
		
		// Add label and text field
		initInput(new JLabel("Name"), 0, 1);
		textField = new JTextField(20);
		initInput(textField, 1, 1);
		inputs.put("Name", textField);
	}
	
	
	private void initInputForYear() {
		
		JComboBox comboBox;
		Vector<Integer> years;
		
		// Add label and combo box
		initInput(new JLabel("Year"), 0, 2);
		years = new Vector<Integer>();
		for (int i=2010; i<2020; ++i) {
			years.add(new Integer(i));
		}
		comboBox = new JComboBox(years);
		initInput(comboBox, 1, 2);
		inputs.put("Year", comboBox);
	}
	
	
	private void initInputForFilename() {
		
		JButton button;
		JTextField textField;
		
		// Add label, text field, and button
		initInput(new JLabel("Filename"), 0, 3);
		textField = new JTextField(20);
		initInput(textField, 1, 3);
		inputs.put("Filename", textField);
		button = new JButton("Browse");
		button.addActionListener(this);
		button.setActionCommand("Browse");
		initInput(button, 2, 3);
	}
	
	
	private void initInputForDescription() {
		
		GridBagConstraints gbc;
		JLabel label;
		JTextArea textArea;
		JScrollPane scrollPane;
		
		// Add label
		label = new JLabel("Description");
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(2, 4, 2, 4);
		inputPanel.add(label, gbc);
		
		// Add text area with scroll pane
		textArea = new JTextArea(4, 28);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane = new JScrollPane(textArea);
		inputs.put("Description", textArea);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(2, 4, 2, 4);
		inputPanel.add(scrollPane, gbc);
	}
	
	
	private void initInputPanel()
	                            throws SQLException {
		
		// Create and add to content pane
		inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
		contentPane.add(inputPanel);
		
		// Add labels and fields
		inputs = new TreeMap<String,JComponent>();
		initInputForSponsor();
		initInputForName();
		initInputForYear();
		initInputForFilename();
		initInputForDescription();
	}
	
	
	/**
	 * Test for %SpotDialog.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		SpotDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotDialog");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("SpotDialog Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(640, 480));
			frame.pack();
			frame.setVisible(true);
			
			// Create dialog
			dialog = new SpotDialog(frame, "New Spot");
			dialog.pack();
			dialog.setLocationRelativeTo(frame);
			dialog.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SpotDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

