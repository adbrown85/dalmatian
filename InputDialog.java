/*
 * InputDialog.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;



/**
 * Dialog for taking input from the user.
 */
public class InputDialog extends JDialog
                         implements ActionListener {
	
	protected ButtonPanel buttonPanel;
	protected InputPanel inputPanel;
	protected final JFrame frame;
	private JPanel contentPane;
	private Vector<ActionListener> listeners;
	
	
	/**
	 * Creates a new %SpotDialog.
	 */
	public InputDialog(JFrame frame,
	                   String title) {
		
		// Initialize
		super(frame, title);
		this.frame = frame;
		init();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		System.err.printf("[InputDialog] Received \"%s\" event.\n",
		                  event.getActionCommand());
	}
	
	
	public void addActionListener(ActionListener listener) {
		
		listeners.add(listener);
	}
	
	
	protected void addButton(String name) {
		
		buttonPanel.addButton(name);
	}
	
	
	protected void addInput(String name,
	                        JComponent input) {
		
		inputPanel.addInput(name, input);
	}
	
	
	protected void fireActionEvent(String command) {
		
		ActionEvent event;
		
		// Send to each listener
		event = new ActionEvent(this, 0, command);
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	
	public void clear() {
		
		inputPanel.clear();
	}
	
	
	public String getFilenameFrom(String input) {
		
		String text;
		
		text = ((FilenameInput)inputPanel.getInput(input)).getText();
		if (text.isEmpty()) {
			return null;
		} else {
			return text;
		}
	}
	
	
	public JComponent getInput(String input) {
		
		return inputPanel.getInput(input);
	}
	
	
	public Object getItemFrom(String input) {
		
		return ((JComboBox)inputPanel.getInput(input)).getSelectedItem();
	}
	
	
	public String getTextFrom(String input) {
		
		String text;
		
		text = ((JTextComponent)inputPanel.getInput(input)).getText();
		if (text.isEmpty()) {
			return null;
		} else {
			return text;
		}
	}
	
	
	private void init() {
		
		// Miscellaneous
		setResizable(false);
		listeners = new Vector<ActionListener>();
		
		// Panels
		initContentPane();
		initInputPanel();
		initButtonPanel();
	}
	
	
	private void initButtonPanel() {
		
		// Create and add panel
		buttonPanel = new ButtonPanel();
		buttonPanel.addActionListener(this);
		contentPane.add(buttonPanel);
	}
	
	
	private void initContentPane() {
		
		// Change content pane layout
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		setContentPane(contentPane);
	}
	
	
	private void initInputPanel() {
		
		// Create and add to content pane
		inputPanel = new InputPanel("Input");
		inputPanel.addActionListener(this);
		contentPane.add(inputPanel);
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
		dialog = new InputDialog(frame, "New Spot");
		dialog.addInput("Combo Box", new JComboBox(options));
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

