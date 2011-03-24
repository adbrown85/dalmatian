package gui;
/*
 * InputPanel.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.JTextComponent;


/**
 * Panel containing input fields with labels.
 */
public class InputPanel extends JPanel implements ActionListener {
	
   protected final List<ActionListener> listeners;
	protected final Map<String,JComponent> inputs;
	protected int row;
	
	/**
	 * Creates a new, empty input panel.
	 * 
	 * @param title Text for the titled border around the panel
	 */
	public InputPanel(String title) {
		
		super(new GridBagLayout());
		
		this.row = -1;
      this.inputs = new HashMap<String,JComponent>();
      this.listeners = new ArrayList<ActionListener>();
		
		setBorder(BorderFactory.createTitledBorder(title));
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
	protected void addLabel(String name, int row, int anchor) {
		
		JLabel label = new JLabel(name);
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Setup constraints
		gbc.anchor = anchor;
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.weightx = 0;
		gbc.insets = new Insets(2, 4, 2, 4);
		
		// Add component
		add(label, gbc);
	}
	
	/**
	 * Adds an input to the panel with a label.
	 * 
	 * <p>If the input is an instance of %JTextArea, it will be wrapped
	 * in an instance of JScrollPane for display.  Note that getInput
	 * will still return the JTextArea.  Also, it will automatically
	 * enable word wrapping at word breaks on the text area.
	 * 
	 * @param name Text for the label
	 * @param input Component that accepts input from the user
	 */
	public void addInput(String name, JComponent input) {
		
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
	protected void addInput(String name, int row, JComponent input) {
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Setup constraints
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
			((JTextComponent) input).setMargin(new Insets(0,2,0,2));
		}
		if (input instanceof JTextArea) {
			((JTextArea) input).setLineWrap(true);
			((JTextArea) input).setWrapStyleWord(true);
			input = new JScrollPane(input);
		}
		add(input, gbc);
		
		// Add listener
		if (input instanceof JComboBox) {
			((JComboBox) input).setActionCommand(name);
			((JComboBox) input).addActionListener(this);
		}
	}
	
	/**
	 * Clears all text components and resets combo boxes.
	 */
	public void clear() {
		for (JComponent input : inputs.values()) {
			if (input instanceof JTextComponent) {
				((JTextComponent) input).setText(null);
			} else if (input instanceof JComboBox) {
				((JComboBox) input).setSelectedIndex(0);
			} else if (input instanceof FilenameInput) {
				((FilenameInput) input).setText(null);
			}
		}
	}
	
	//------------------------------------------------------------
   // Getters
   //
	
	/**
	 * Returns one of the inputs according to its name.
	 * 
	 * @param name Name of input field
	 * @return Input field, or <tt>null</tt> if not stored
	 */
	public JComponent getInput(String name) {
		return inputs.get(name);
	}
	
	/**
	 * Returns an item from a combo box.
	 * 
	 * @param name Name of the combo box
	 * @return Selected item, or <tt>null</tt> if N/A
	 */
	public Object getItemFrom(String name) {
		
		JComponent input = getInput(name);
		
		if (input instanceof JComboBox) {
			return ((JComboBox) input).getSelectedItem();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the text in an input field.
	 * 
	 * @param name Name of the input field
	 * @return Text in field, or <tt>null</tt> if N/A or text is empty
	 */
	public String getTextFrom(String name) {
		
		JComponent input = getInput(name);
		String text;
		
		// Get text
		if (input instanceof FilenameInput) {
			text = ((FilenameInput) input).getText();
		} else if (input instanceof JTextComponent) {
			text = ((JTextComponent) input).getText();
		} else {
			return null;
		}
		return (text.isEmpty()) ? null : text;
	}
	
	/**
	 * Returns the timestamp in an input field.
	 * 
	 * @param name name of the input field
	 * @return Timestamp in field, or <tt>null</tt> if N/A
	 */
	public Timestamp getTimestampFrom(String name) {
		
		JComponent input = getInput(name);
		
		if (input instanceof TimestampEditor) {
			return ((TimestampEditor) input).getTimestamp();
		} else {
			return null;
		}
	}
	
	/**
	 * Changes the text in an input field.
	 * 
	 * @param name Name of the input field
	 * @param text Text to change input field to
	 */
	public void setTextIn(String name, String text) {
		
		JComponent input = getInput(name);
		
		if (input instanceof FilenameInput) {
			((FilenameInput) input).setText(text);
		} else if (input instanceof JTextComponent) {
			((JTextComponent) input).setText(text);
		}
	}
	
	/**
	 * Changes the selected item in a combo box.
	 * 
	 * @param name Name of the combo box
	 * @param item Item to select
	 */
	public void setItemIn(String name, Object item) {
		
		JComponent input = getInput(name);
		
		if (input instanceof JComboBox) {
			((JComboBox) input).setSelectedItem(item);
		}
	}
	
	/**
	 * Changes the timestamp in an input field.
	 * 
	 * @param name Name of the input field
	 * @param timestamp Timestamp to change field to
	 * @throws SQLException if timestamp is invalid
	 */
	public void setTimestampIn(String name,
	                           Timestamp timestamp)
	                           throws SQLException {
		
		JComponent input = getInput(name);
		
		if (input instanceof TimestampEditor) {
			((TimestampEditor) input).setTimestamp(timestamp);
		}
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		
		final InputPanel panel = new InputPanel("Input");
		JButton button = new JButton("Clear");
		JPanel pane = new JPanel();
		JFrame frame = new JFrame("Input Panel");
		
		// Create input panel
		panel.addInput("Name", new JTextField(20));
		panel.addInput("Description", new JTextArea(4, 28));
		
		// Create button
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				panel.clear();
			}
		});
		
		// Setup pane
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(panel);
		pane.add(button);
		
		// Create and show frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(pane);
		frame.pack();
		frame.setVisible(true);
	}
}

