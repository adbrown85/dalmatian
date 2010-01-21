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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;



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
	
	
	protected void fireActionEvent(String command) {
		
		ActionEvent event;
		
		// Send to each listener
		event = new ActionEvent(this, 0, command);
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
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
		contentPane.add(inputPanel);
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		InputDialog dialog;
		
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

