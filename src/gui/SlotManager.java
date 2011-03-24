package gui;
/*
 * SlotManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import Break;
import Database;
import SlotTable;
import Spot;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;


/**
 * Manages slots in a break.
 */
public class SlotManager extends DatabaseTableManager {
	
	Component spotSelectorParent;
	SpotSelector spotSelector;
	
	public SlotManager(Frame frame, Break b) throws SQLException {
		
		super(new SlotTable(b));
		
		// Dialogs
		spotSelector = new SpotSelector(frame, "Select Spot");
		spotSelector.addActionListener(this);
		spotSelectorParent = this;
		
		// Buttons
		addButton("Add");
		addButton("Remove");
	}
	
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if (command.equals("Add")) {
			handleAdd();
		} else if (command.equals("Remove")) {
			handleRemove();
		} else if (command.equals("Selected")) {
			handleSelected();
		}
	}
	
	public void commit() throws SQLException {
		
		Break b;
		String sql;
		
		try {
			
			// Start transaction
			Database.executeUpdate("START TRANSACTION");
			
			// Insert break, or update break and clear slots
			b = ((SlotTable)table).getBreak();
			if (b.getId() == 0) {
				b.insert();
			} else {
				Break.update(b, b);
				sql = "  DELETE FROM slot WHERE break=" + b.getId();
				Database.executeUpdate(sql);
			}
			
			// Insert slots back into the break and commit
			sql = "  INSERT INTO slot(break,position,spot) " + 
			      "  VALUES " + getValues(b);
			Database.executeUpdate(sql);
			
			// Commit
			Database.executeUpdate("COMMIT");
		}
		catch (SQLException e) {
			Database.executeUpdate("ROLLBACK");
			throw e;
		}
	}
	
	private String getValues(Break b) {
		
		int id = b.getId();
		int rowCount = table.getRowCount();
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<rowCount; ++i) {
			sb.append(String.format("(%d, %d, %d)",
			                        id,
			                        i + 1,
			                        table.getValueAt(i, "spot")));
			if (i < rowCount-1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	private void handleAdd() {
		spotSelector.pack();
		spotSelector.setLocationRelativeTo(spotSelectorParent);
		spotSelector.setVisible(true);
	}
	
	private void handleRemove() {
		
		int row = getSelectedRow();
		
		if (row != -1) {
			((SlotTable) table).remove(getSelectedRow());
		} else {
			GUI.showMessage(spotSelectorParent, "Please select a slot.");
		}
	}
	
	
	private void handleSelected() {
		
		Spot spot;
		
		try {
		   spot = spotSelector.getSpot();
			((SlotTable) table).add(spot);
		} catch (SQLException e) {
			GUI.showError(spotSelectorParent, "Could not add spot.");
		}
	}
	
	public void setBreak(Break _break) {
		((SlotTable) table).setBreak(_break);
	}
	
	public void setSpotSelectorParent(Component component) {
		this.spotSelectorParent = component;
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws SQLException {
		
		Box box = new Box(BoxLayout.PAGE_AXIS);
		JButton button = new JButton("Commit");
		final JFrame frame = new JFrame("Slot Manager");
		final SlotManager sm = new SlotManager(frame, new Break(12));
		
		// Make frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Make slot manager in box
		sm.setBorder(BorderFactory.createTitledBorder("Slots"));
		box.add(sm);
		
		// Add commit button
		button.setAlignmentX(0.5f);
		box.add(button);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				try {
					sm.commit();
					GUI.showMessage(frame, "Committed.");
				} catch (SQLException e) {
					GUI.showError(frame, "Could not commit.");
					e.printStackTrace();
				}
			}
		});
		
		// Show in frame
		frame.setContentPane(box);
		frame.pack();
		frame.setVisible(true);
	}
}

