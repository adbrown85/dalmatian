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
		
		int row = table.getSelectedRow();
		String command = event.getActionCommand();
		
		System.out.printf("[DatabaseTableManager] Row %d selected.\n", row);
		System.out.println("[DatabaseTableManager] " + command);
	}
	
	public void addButton(String name) {
		buttonPanel.addButton(name);
	}
	
	public Frame getFrame() {
		return JOptionPane.getFrameForComponent(this);
	}
	
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public Object getValueAt(int row, int column) {
		return table.getValueAt(row, column);
	}
	
	public Object getValueAt(int row, String columnName) {
		return table.getValueAt(row, columnName);
	}
	
	public boolean hasSelected() {
		return getSelectedRow() != -1;
	}
	
	private void init(DatabaseTable table) throws SQLException {
		
		// Appearance
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setMaximumSize(GUI.getInfiniteSize());
		
		// Table
		this.table = table;
		scrollPane = GUI.getScrollPaneFor(table);
		add(scrollPane);
		
		// Button Panel
		buttonPanel = new ButtonPanel();
		buttonPanel.addActionListener(this);
		add(buttonPanel);
	}
	
	protected void refresh() {
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
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws SQLException {
		
	   JFrame frame = new JFrame("DatabaseTableManager");
	   String sql = "SELECT * FROM sponsor ORDER BY name";
		DatabaseTableManager dtm = new DatabaseTableManager(sql);
		
		// Create manager and add it to the frame
		dtm.addButton("New");
		dtm.addButton("Edit");
		dtm.addButton("Delete");
		
		// Add it to the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(dtm);
		frame.pack();
		frame.setVisible(true);
	}
}

