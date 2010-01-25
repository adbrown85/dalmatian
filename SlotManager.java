/*
 * SlotManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;



/**
 * 
 */
public class SlotManager extends DatabaseTableManager {
	
	Component spotSelectorParent;
	SpotSelector spotSelector;
	
	
	public SlotManager(JFrame frame,
	                   Break _break)
	                   throws SQLException {
		
		super(new SlotTableModel(_break));
		
		// Dialogs
		spotSelector = new SpotSelector(frame, "Select Spot");
		spotSelector.addActionListener(this);
		spotSelectorParent = this;
		
		// Buttons
		addButton("Add");
		addButton("Remove");
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		String command;
		
		command = event.getActionCommand();
		if (command.equals("Add")) {
			handleAdd();
		} else if (command.equals("Remove")) {
			handleRemove();
		} else if (command.equals("Selected")) {
			handleSelected();
		}
	}
	
	
	public void commit()
	                   throws SQLException {
		
		((SlotTableModel)tableModel).commit();
	}
	
	
	private void handleAdd() {
		
		spotSelector.pack();
		spotSelector.setLocationRelativeTo(spotSelectorParent);
		spotSelector.setVisible(true);
	}
	
	
	private void handleRemove() {
		
		int row;
		
		row = getSelectedRow();
		if (row != -1) {
			((SlotTableModel)tableModel).remove(getSelectedRow());
		} else {
			GUI.showMessage(spotSelectorParent, "Please select a slot.");
		}
	}
	
	
	private void handleSelected() {
		
		Spot spot;
		
		try {
			spot = spotSelector.getSpot();
			((SlotTableModel)tableModel).add(spot);
		} catch (SQLException e) {
			GUI.showError(spotSelectorParent, "Could not add spot.");
		}
	}
	
	
	public void setSpotSelectorParent(Component component) {
		
		this.spotSelectorParent = component;
	}
	
	
	public void setBreak(Break _break) {
		
		((SlotTableModel)tableModel).setBreak(_break);
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotManager");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			// Test
			frame = new JFrame("Slot Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SlotManager(frame, new Break(12)));
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

