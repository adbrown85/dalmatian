/*
 * DatabaseTableManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.SQLException;



/**
 * Manages a database table.
 */
public class DatabaseTableManager extends JPanel
                                  implements ActionListener {
	
	private final int HEIGHT=480, WIDTH=640;
	protected ButtonPanel buttonPanel;
	protected DatabaseTable databaseTable;
	protected JFrame frame;
	
	
	public DatabaseTableManager(JFrame frame,
	                            String sql)
	                            throws SQLException {
		
		// Create as panel with a box layout
		super();
		init(frame, sql);
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		int row;
		String command;
		
		// Get row and key
		row = databaseTable.getSelectedRow();
		System.out.printf("[DatabaseTableManager] Row %d selected.\n", row);
		
		// Handle command
		command = event.getActionCommand();
		System.out.println("[DatabaseTableManager] " + command);
	}
	
	
	public void addButton(String name) {
		
		buttonPanel.addButton(name);
	}
	
	
	public int getSelectedRow() {
		
		return databaseTable.getSelectedRow();
	}
	
	
	public Object getValueAt(int row,
	                         int column) {
		
		return databaseTable.getValueAt(row, column);
	}
	
	
	public boolean hasSelected() {
		
		return getSelectedRow() != -1;
	}
	
	
	private void init(JFrame frame,
	                  String sql) 
	                  throws SQLException {
		
		// Miscellaneous
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		this.frame = frame;
		
		// Initialize components
		initDatabaseTable(sql);
		initButtonPanel();
	}
	
	
	private void initButtonPanel() {
		
		// Create the panel
		buttonPanel = new ButtonPanel();
		buttonPanel.addActionListener(this);
		add(buttonPanel);
	}
	
	
	private void initDatabaseTable(String sql)
	                               throws SQLException {
		
		int scrollBarPolicy=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		JScrollPane scrollPane;
		
		// Create table and add it with a scroll pane
		databaseTable = new DatabaseTable(sql);
		scrollPane = new JScrollPane(databaseTable);
		scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE,
		                                        Integer.MAX_VALUE));
		scrollPane.setVerticalScrollBarPolicy(scrollBarPolicy);
		add(scrollPane);
	}
	
	
	protected void refresh() {
		
		// Refresh data
		try {
			databaseTable.refresh();
		} catch (SQLException e) {
			System.err.println("[DatabaseTableManager] Could not refresh.");
		}
	}
	
	
	protected boolean showConfirmDelete(String item) {
		
		int option, style;
		String message, title;
		
		// Show confirm dialog
		message = "Are you sure you want to delete \"" + item + "\"?";
		title = "Confirm Delete";
		style = JOptionPane.YES_NO_OPTION;
		option = JOptionPane.showConfirmDialog(frame, message, title, style);
		return option == JOptionPane.YES_OPTION;
	}
	
	
	protected void showSuccessfulDelete(String item) {
		
		int type;
		String message, title;
		
		// Show information dialog
		message = "Successfully deleted \"" + item + "\".";
		title = "Successful Delete";
		type = JOptionPane.INFORMATION_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, type);
	}
	
	
	protected void showUnsuccessfulDelete(String item) {
		
		int type;
		String message, title;
		
		// Show information dialog
		message = "Could not delete \"" + item + "\".";
		title = "Unsuccessful Delete";
		type = JOptionPane.ERROR_MESSAGE;
		JOptionPane.showMessageDialog(frame, message, title, type);
	}
	
	
	public static void main(String[] args) {
		
		DatabaseTableManager databaseTableManager;
		JFrame frame;
		String sql;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DatabaseTableManager");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("DatabaseTableManager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// Create manager and add it to the frame
			sql = "SELECT * FROM sponsor ORDER BY name";
			databaseTableManager = new DatabaseTableManager(frame, sql);
			databaseTableManager.addButton("New");
			databaseTableManager.addButton("Edit");
			databaseTableManager.addButton("Delete");
			
			// Add it to the frame
			frame.setContentPane(databaseTableManager);
			frame.pack();
			frame.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("DatabaseTableManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

