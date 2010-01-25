/*
 * InputPanel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.JTextComponent;



/**
 * Panel containing input fields with labels.
 */
public class InputPanel extends JPanel
                        implements ActionListener {
	
	protected int row=-1;
	protected TreeMap<String,JComponent> inputs;
	protected Vector<ActionListener> listeners;
	
	
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
		listeners = new Vector<ActionListener>();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	
	public void addActionListener(ActionListener listener) {
		
		listeners.add(listener);
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
		gbc.weightx = 0;
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
	 * return the %JTextArea.  Also, it will automatically enable word 
	 * wrapping at word breaks on the text area.
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
		gbc.weightx = 1;
		if (input instanceof JTextArea) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
		}
		gbc.insets = new Insets(2, 4, 2, 4);
		
		// Add and store component
		inputs.put(name, input);
		if (input instanceof JTextComponent) {
			((JTextComponent)input).setMargin(new Insets(0,2,0,2));
		}
		if (input instanceof JTextArea) {
			((JTextArea)input).setLineWrap(true);
			((JTextArea)input).setWrapStyleWord(true);
			input = new JScrollPane(input);
		}
		add(input, gbc);
		
		// Add listener
		if (input instanceof JComboBox) {
			((JComboBox)input).setActionCommand(name);
			((JComboBox)input).addActionListener(this);
		}
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
			} else if (input instanceof FilenameInput) {
				((FilenameInput)input).setText(null);
			}
		}
	}
	
	
	/**
	 * Gets one of the inputs according to its name.
	 */
	public JComponent getInput(String name) {
		
		return inputs.get(name);
	}
	
	
	public Object getItemFrom(String inputName) {
		
		JComponent input;
		
		input = getInput(inputName);
		if (input instanceof JComboBox) {
			return ((JComboBox)input).getSelectedItem();
		} else {
			return null;
		}
	}
	
	
	public String getTextFrom(String inputName) {
		
		JComponent input;
		String text;
		
		// Get text
		input = getInput(inputName);
		if (input instanceof FilenameInput) {
			text = ((FilenameInput)input).getText();
		} else if (input instanceof JTextComponent) {
			text = ((JTextComponent)input).getText();
		} else {
			return null;
		}
		
		// Return null if it's empty
		if (text.isEmpty()) {
			return null;
		} else {
			return text;
		}
	}
	
	
	public Timestamp getTimestampFrom(String inputName) {
		
		JComponent input;
		
		input = getInput(inputName);
		if (input instanceof TimestampEditor) {
			return ((TimestampEditor)input).getTimestamp();
		} else {
			return null;
		}
	}
	
	
	public void setTextIn(String inputName,
	                      String text) {
		
		JComponent input;
		
		input = getInput(inputName);
		if (input instanceof FilenameInput) {
			((FilenameInput)input).setText(text);
		} else if (input instanceof JTextComponent) {
			((JTextComponent)input).setText(text);
		}
	}
	
	
	public void setItemIn(String inputName,
	                      Object item) {
		
		JComponent input;
		
		input = getInput(inputName);
		if (input instanceof JComboBox) {
			((JComboBox)input).setSelectedItem(item);
		}
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

