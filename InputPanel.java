/*
 * InputPanel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.text.JTextComponent;



/**
 * Panel containing input fields with labels.
 */
public class InputPanel extends JPanel {
	
	protected int row=-1;
	protected TreeMap<String,JComponent> inputs;
	
	
	/**
	 * Creates a new, empty input panel.
	 * 
	 * @param title
	 *     Text for the titled border around the panel.
	 */
	public InputPanel(String title) {
		
		// Initialize
		super(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(title));
		inputs = new TreeMap<String,JComponent>();
	}
	
	
	/**
	 * Adds a label to the input panel.
	 */
	protected void addLabel(String name,
	                        int row,
	                        int anchor) {
		
		JLabel label;
		GridBagConstraints gbc;
		
		// Setup constraints
		gbc = new GridBagConstraints();
		gbc.anchor = anchor;
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.insets = new Insets(2, 4, 2, 4);
		
		// Add component
		label = new JLabel(name);
		add(label, gbc);
	}
	
	
	/**
	 * Adds an input to the panel with a label.
	 * 
	 * If the input is an instance of %JTextArea, it will be wrapped in an 
	 * instance of %JScrollPane for display.  Note that getInput will still 
	 * return the %JTextArea.
	 * 
	 * @param name
	 *     Text for the label.
	 * @param input
	 *     Component that accepts input from the user.
	 */
	public void addInput(String name,
	                     JComponent input) {
		
		int anchor;
		
		// Set up constraints
		++row;
		if (input instanceof JTextArea || input instanceof JScrollPane) {
			anchor = GridBagConstraints.FIRST_LINE_START;
		} else {
			anchor = GridBagConstraints.LINE_START;
		}
		
		// Add label and input
		addLabel(name, row, anchor);
		addInput(name, row, input);
	}
	
	
	/**
	 * Adds an input component to the panel and stores it.
	 */
	protected void addInput(String name,
	                        int row,
	                        JComponent input) {
		
		GridBagConstraints gbc;
		
		// Setup constraints
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 1;
		gbc.gridy = row;
		gbc.insets = new Insets(2, 4, 2, 4);
		
		// Add and store component
		inputs.put(name, input);
		if (input instanceof JTextArea) {
			input = new JScrollPane(input);
		}
		add(input, gbc);
	}
	
	
	/**
	 * Clears all text components and resets combo boxes.
	 */
	public void clear() {
		
		for (JComponent input : inputs.values()) {
			if (input instanceof JTextComponent) {
				((JTextComponent)input).setText(null);
			} else if (input instanceof JComboBox) {
				((JComboBox)input).setSelectedIndex(0);
			}
		}
	}
	
	
	/**
	 * Gets one of the inputs according to its name.
	 */
	public JComponent getInput(String name) {
		
		return inputs.get(name);
	}
	
	
	public static void main(String[] args) {
		
		final InputPanel panel;
		JButton button;
		JPanel pane;
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("InputPanel");
		System.out.println("****************************************");
		System.out.println();
		
		// Create input panel
		panel = new InputPanel("Input");
		panel.addInput("Name", new JTextField(20));
		panel.addInput("Description", new JTextArea(4, 28));
		
		// Create button
		button = new JButton("Clear");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				panel.clear();
			}
		});
		
		// Setup pane
		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(panel);
		pane.add(button);
		
		// Create and show frame
		frame = new JFrame("Input Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(pane);
		frame.pack();
		frame.setVisible(true);
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("InputPanel");
		System.out.println("****************************************");
		System.out.println();
	}
}

