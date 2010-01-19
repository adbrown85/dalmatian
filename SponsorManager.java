/*
 * SponsorManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import java.sql.SQLException;



/**
 * Manages sponsors.
 */
public class SponsorManager extends JPanel
                            implements ActionListener {
	
	private final int BORDER=4;
	private JFrame frame;
	private JScrollPane scrollPane;
	private DatabaseTable table;
	
	
	/**
	 * Creates a new %SponsorsManager.
	 */
	public SponsorManager(JFrame frame)
	                      throws SQLException {
		
		// Create as panel with a box layout
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		
		// Store frame for dialogs
		this.frame = frame;
		
		// Initialize components
		initTable();
		initButtons();
	}
	
	
	/**
	 * Triggered when an action is fired.
	 */
	public void actionPerformed(ActionEvent event) {
		
		int row;
		String key, command;
		
		// Get row and key
		row = table.getSelectedRow();
		key = null;
		if (row != -1)
			key = (String)table.getValueAt(row, 0);
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("Delete")) {
			handleDelete(key);
		} else if (command.equals("Edit")) {
			handleEdit(key);
		} else if (command.equals("New")) {
			handleNew();
		} else if (command.equals("Refresh")) {
			handleRefresh();
		}
	}
	
	
	private void handleDelete(String name) {
		
		int option;
		String message, title;
		
		// Check for bad name
		if (name == null)
			return;
		
		// Confirm
		message = "Are you sure you want to delete \""
		          + name + "\"?";
		title = "Confirm Delete";
		option = JOptionPane.showConfirmDialog(frame,
		                                       message,
		                                       title,
		                                       JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			try {
				Sponsor.delete(name);
				System.out.printf("[SponsorManager] Deleted \"%s\"\n", name);
				handleRefresh();
			} catch (SQLException e) {
				System.out.printf("[SponsorManager] Could not delete \"%s\".\n", name);
			}
		}
	}
	
	
	private void handleEdit(String name) {
		
		// Check for bad name
		if (name == null)
			return;
		
		System.out.printf("[SponsorManager] Should edit \"%s\"\n", name);
	}
	
	
	/**
	 * Action for the "New" command.
	 */
	private void handleNew() {
		
		SponsorDialog dialog;
		
		// Show new sponsor dialog
		dialog = new SponsorDialog(frame, "New Sponsor");
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
		dialog.addActionListener(this);
	}
	
	
	/**
	 * Action for when the manager should be refreshed.
	 */
	private void handleRefresh() {
		
		// Refresh data
		try {
			table.refresh();
		} catch (SQLException e) {
			System.err.println("[SponsorManager] Could not refresh data.");
		}
	}
	
	
	/**
	 * Initializes the buttons.
	 */
	private void initButtons() {
		
		JButton button;
		JPanel panel;
		String[] buttonNames={"New", "Edit", "Delete"};
		
		// Add buttons to panel
		panel = new JPanel();
		for (int i=0; i<buttonNames.length; ++i) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.addActionListener(this);
			panel.add(button);
		}
		
		// Add the panel
		add(panel);
	}
	
	
	/**
	 * Initializes the table.
	 */
	private void initTable()
	                       throws SQLException {
		
		// Create table
		table = new DatabaseTable("SELECT * FROM sponsor");
		
		// Add it with a scroll pane
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(640, 480));
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}
	
	
	/**
	 * Test for %SponsorManager.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorManager");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		try {
			frame = new JFrame("SponsorManager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SponsorManager(frame));
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

