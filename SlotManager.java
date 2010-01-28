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
 * Manages slots in a break.
 */
public class SlotManager extends DatabaseTableManager {
	
	Component spotSelectorParent;
	SpotSelector spotSelector;
	
	
	public SlotManager(Frame frame,
	                   Break _break)
	                   throws SQLException {
		
		super(new SlotTable(_break));
		
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
		
		Break _break;
		String sql;
		
		try {
			
			// Start transation
			Database.executeUpdate("START TRANSACTION");
			
			// Insert break, or update break and clear slots
			_break = ((SlotTable)table).getBreak();
			if (_break.getId() == 0) {
				_break.insert();
			} else {
				Break.update(_break, _break);
				sql = "  DELETE FROM slot WHERE break=" + _break.getId();
				Database.executeUpdate(sql);
			}
			
			// Insert slots back into the break and commit
			sql = "  INSERT INTO slot(break,position,spot) " + 
			      "  VALUES " + getValues(_break);
			Database.executeUpdate(sql);
			
			// Commit
			Database.executeUpdate("COMMIT");
		}
		catch (SQLException e) {
			Database.executeUpdate("ROLLBACK");
			throw e;
		}
	}
	
	
	private String getValues(Break _break) {
		
		int breakId, rowCount;
		String values="";
		
		breakId = _break.getId();
		rowCount = table.getRowCount();
		for (int i=0; i<rowCount; ++i) {
			values += String.format("(%d, %d, %d)",
			                        breakId,
			                        i+1,
			                        table.getValueAt(i,"spot"));
			if (i < rowCount-1) {
				values += ", ";
			}
		}
		return values;
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
			((SlotTable)table).remove(getSelectedRow());
		} else {
			GUI.showMessage(spotSelectorParent, "Please select a slot.");
		}
	}
	
	
	private void handleSelected() {
		
		Spot spot;
		
		try {
			spot = spotSelector.getSpot();
			((SlotTable)table).add(spot);
		} catch (SQLException e) {
			GUI.showError(spotSelectorParent, "Could not add spot.");
		}
	}
	
	
	public void setBreak(Break _break) {
		
		((SlotTable)table).setBreak(_break);
	}
	
	
	public void setSpotSelectorParent(Component component) {
		
		this.spotSelectorParent = component;
	}
	
	
	public static void main(String[] args) {
		
		Box box;
		JButton button;
		final JFrame frame;
		final SlotManager slotManager;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SlotManager");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Make frame
			frame = new JFrame("Slot Manager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// Make slot manager in box
			box = new Box(BoxLayout.PAGE_AXIS);
			slotManager = new SlotManager(frame, new Break(12));
			slotManager.setBorder(BorderFactory.createTitledBorder("Slots"));
			box.add(slotManager);
			
			// Add commit button
			button = new JButton("Commit");
			button.setAlignmentX(0.5f);
			box.add(button);
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event) {
					try {
						slotManager.commit();
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
		catch (SQLException e) {
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

