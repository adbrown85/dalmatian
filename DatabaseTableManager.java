/*
 * DatabaseTableManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.SQLException;



/**
 * Manages a database table.
 */
public class DatabaseTableManager extends JPanel
                                  implements ActionListener {
	
	private final int HEIGHT=480, WIDTH=640;
	protected ButtonPanel buttonPanel;
	protected DatabaseTable table;
	protected JFrame frame;
	protected JScrollPane scrollPane;
	
	
	public DatabaseTableManager(DatabaseTable table)
	                            throws SQLException {
		
		super();
		init(table);
	}
	
	
	public DatabaseTableManager(String sql)
	                            throws SQLException {
		
		super();
		init(new DatabaseTable(sql));
	}
	
	
	public DatabaseTableManager(String sql,
	                            String[] hiddenColumns)
	                            throws SQLException {
		
		super();
		init(new DatabaseTable(sql, hiddenColumns));
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		int row;
		String command;
		
		// Get row and key
		row = table.getSelectedRow();
		System.out.printf("[DatabaseTableManager] Row %d selected.\n", row);
		
		// Handle command
		command = event.getActionCommand();
		System.out.println("[DatabaseTableManager] " + command);
	}
	
	
	public void addButton(String name) {
		
		buttonPanel.addButton(name);
	}
	
	
	public int getSelectedRow() {
		
		return table.getSelectedRow();
	}
	
	
	public Object getValueAt(int row,
	                         int column) {
		
		return table.getValueAt(row, column);
	}
	
	
	public Object getValueAt(int row,
	                         String columnName) {
		
		return table.getValueAt(row, columnName);
	}
	
	
	public boolean hasSelected() {
		
		return getSelectedRow() != -1;
	}
	
	
	private void init(DatabaseTable table)
	                  throws SQLException {
		
		// Appearance
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setMaximumSize(GUI.getInfiniteSize());
		
		// Table
		scrollPane = GUI.getScrollPaneFor(table);
		add(scrollPane);
		
		// Button Panel
		buttonPanel = new ButtonPanel();
		buttonPanel.addActionListener(this);
		add(buttonPanel);
	}
	
	
	protected void refresh() {
		
		// Refresh data
		try {
			table.refresh();
		} catch (SQLException e) {
			System.err.println("[DatabaseTableManager] Could not refresh.");
		}
	}
	
	
	protected void setTableBorder(Border border) {
		
		GUI.setScrollPaneBorder(scrollPane, border);
	}
	
	
	protected boolean showConfirmDelete(String item) {
		
		int option, style;
		String message, title;
		
		// Show confirm dialog
		message = "Are you sure you want to delete \"" + item + "\"?";
		title = "Confirm Delete";
		style = JOptionPane.YES_NO_OPTION;
		option = JOptionPane.showConfirmDialog(this, message, title, style);
		return option == JOptionPane.YES_OPTION;
	}
	
	
	protected void showSuccessfulDelete(String item) {
		
		int type;
		String message, title;
		
		// Show information dialog
		message = "Successfully deleted \"" + item + "\".";
		title = "Successful Delete";
		type = JOptionPane.INFORMATION_MESSAGE;
		JOptionPane.showMessageDialog(this, message, title, type);
	}
	
	
	protected void showUnsuccessfulDelete(String item) {
		
		int type;
		String message, title;
		
		// Show information dialog
		message = "Could not delete \"" + item + "\".";
		title = "Unsuccessful Delete";
		type = JOptionPane.ERROR_MESSAGE;
		JOptionPane.showMessageDialog(this, message, title, type);
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
			databaseTableManager = new DatabaseTableManager(sql);
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

