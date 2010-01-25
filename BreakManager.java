/*
 * BreakManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;



/**
 * Manages the break table.
 */
public class BreakManager extends DatabaseTableManager {
	
	private static final String sql;
	private BreakInsertDialog insertDialog;
	private BreakUpdateDialog updateDialog;
	
	
	static {
		sql = "SELECT id,start,end FROM break ORDER BY start";
	}
	
	
	public BreakManager(JFrame frame)
	                    throws SQLException {
		
		super(new HidableDatabaseTableModel(sql, getHiddenColumns()));
		
		// Buttons
		addButton("Insert");
		addButton("Update");
		addButton("Delete");
		addButton("Refresh");
		
		// Dialogs
		insertDialog = new BreakInsertDialog(frame);
		insertDialog.pack();
		insertDialog.addActionListener(this);
		updateDialog = new BreakUpdateDialog(frame);
		updateDialog.pack();
		updateDialog.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		// Handle command
		command = event.getActionCommand();
		if (command.equals("Insert")) {
			handleInsert();
		} else if (command.equals("Update")) {
			handleUpdate();
		} else if (command.equals("Delete")) {
			handleDelete();
		} else if (command.equals("Refresh")) {
			handleRefresh();
		}
	}
	
	
	private static String[] getHiddenColumns() {
		
		String[] names=null;
		// String[] names={"id"};
		return names;
	}
	
	
	private void handleDelete() {
		
		int id, row;
		
		row = getSelectedRow();
		if (row == -1) {
			GUI.showMessage(this, "Please select a break.");
			return;
		}
		
		try {
			if (!GUI.showConfirmDelete(this))
				return;
			Break.delete((Integer)tableModel.getValueAt(row, "id"));
			refresh();
			GUI.showMessage(this, "Deleted break.");
		} catch (SQLException e) {
			GUI.showError(this, "Could not select break.");
		}
	}
	
	
	private void handleInsert() {
		
		insertDialog.setLocationRelativeTo(this);
		insertDialog.setVisible(true);
	}
	
	
	private void handleRefresh() {
		
		refresh();
	}
	
	
	private void handleUpdate() {
		
		int id, row;
		
		row = getSelectedRow();
		if (row == -1) {
			GUI.showMessage(this, "Please select a break.");
			return;
		}
		
		try {
			id = (Integer)tableModel.getValueAt(row, "id");
			updateDialog.setBreak(new Break(id));
			updateDialog.setLocationRelativeTo(this);
			updateDialog.pack();
			updateDialog.setVisible(true);
		} catch (SQLException e) {
			GUI.showError(this, "Could not update break.");
		}
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakManager");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Create frame
			frame = new JFrame("Break Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new BreakManager(frame));
			frame.pack();
			frame.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

