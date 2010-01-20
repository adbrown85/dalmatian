/*
 * BreakEditor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.TreeMap;
import javax.swing.*;



/**
 * 
 */
public class BreakEditor extends JDialog
                         implements ActionListener {
	
	JPanel contentPane;
	JPanel buttonPanel, dateTimePanel, spotsPanel;
	TreeMap<String,TimestampEditor> timestampEditors;
	
	
	/**
	 * Creates a new %BreakEditor.
	 */
	public BreakEditor(JFrame frame,
	                   String title)
	                   throws SQLException {
		
		// Initialize
		super(frame, title);
		initContentPane();
		initDateTimePanel();
		initSpotsPanel();
		initButtonPanel();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		System.out.println(event.getActionCommand());
	}
	
	
	/**
	 * Initializes the button panel.
	 */
	private void initButtonPanel() {
		
		JButton button;
		String[] names={"Update","Cancel","Play"};
		
		// Create and add panel
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel);
		
		// Add buttons
		for (String name : names) {
			button = new JButton(name);
			button.setActionCommand(name);
			button.addActionListener(this);
			buttonPanel.add(button);
		}
	}
	
	
	private void initContentPane() {
		
		// Create and set content pane
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		setContentPane(contentPane);
	}
	
	
	/**
	 * Initializes the date and time panel.
	 */
	private void initDateTimePanel()
	                               throws SQLException {
		
		String[] names={"Start","End"};
		String title="Date and Time";
		
		// Create and add panel
		dateTimePanel = new JPanel(new GridBagLayout());
		dateTimePanel.setBorder(BorderFactory.createTitledBorder(title));
		contentPane.add(dateTimePanel);
		
		// Add editors with labels
		timestampEditors = new TreeMap<String,TimestampEditor>();
		for (int i=0; i<names.length; ++i) {
			initDateTimeEditor(names[i], i);
		}
	}
	
	
	private void initDateTimeEditor(String name,
	                                int row)
	                                throws SQLException {
		
		GridBagConstraints gbc;
		JLabel label;
		TimestampEditor timestampEditor;
		
		// Add label
		label = new JLabel(name);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 4, 0, 4);
		dateTimePanel.add(label, gbc);
		
		// Add editor
		timestampEditor = new TimestampEditor(null);
		timestampEditors.put(name, timestampEditor);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = row;
		gbc.anchor = GridBagConstraints.LINE_START;
		dateTimePanel.add(timestampEditor, gbc);
	}
	
	
	/**
	 * Initializes the spots panel.
	 */
	private void initSpotsPanel() {
		
	}
	
	
	/**
	 * Test for BreakEditor.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		BreakEditor dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakEditor");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Make frame
			frame = new JFrame("Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(400, 300));
			frame.pack();
			frame.setVisible(true);
			
			// Make dialog
			dialog = new BreakEditor(frame, "Break Editor");
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
		System.out.println("BreakEditor");
		System.out.println("****************************************");
		System.out.println();
	}
}

