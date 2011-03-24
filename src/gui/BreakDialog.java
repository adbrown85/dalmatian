package gui;
/*
 * BreakEditor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import data.Break;


/**
 * Dialog dealing with Breaks.
 */
public class BreakDialog extends InputDialog implements ActionListener {
	
	protected Break original=null;
	protected SlotManager slotManager;
	
	public BreakDialog(Frame frame,
	                   String title)
	                   throws SQLException {
		
		super(frame, title, "Date and Time");
		
		// Inputs
		addInput("Start", new TimestampEditor(null));
		addInput("End", new TimestampEditor(null));
		initInputPanelSize();
		
		// Slots
		original = new Break();
		initSlotManager(new Break());
	}
	
	public Break getBreak() {
		
		Break _break;
		
		_break = new Break();
		_break.setId(original.getId());
		_break.setStart(getTimestampFrom("Start"));
		_break.setEnd(getTimestampFrom("End"));
		return _break;
	}
	
	private void initSlotManager(Break _break) throws SQLException {
		
		// Create 
		slotManager = new SlotManager(frame, _break);
		slotManager.setPreferredSize(new Dimension(500, 200));
		slotManager.setMaximumSize(GUI.getInfiniteSize());
		slotManager.setSpotSelectorParent(this);
		add(slotManager);
		
		// Borders
		slotManager.setBorder(BorderFactory.createTitledBorder("Slots"));
		slotManager.setTableBorder(BorderFactory.createEmptyBorder(0,4,0,4));
	}
	
	
	private void initInputPanelSize() {
		
		int height;
		
		pack();
		height = (int) inputPanel.getPreferredSize().getHeight();
		inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	}
	
	public void setBreak(Break _break) throws SQLException {
		original = new Break(_break);
		setTimestampIn("Start", _break.getStart());
		setTimestampIn("End", _break.getEnd());
	}
	
	public static void main(String[] args) {
		
		JFrame frame;
		BreakDialog dialog;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakDialog");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Make frame
			frame = new JFrame("Frame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(400, 300));
			frame.pack();
			frame.setVisible(true);
			
			// Make dialog
			dialog = new BreakDialog(frame, "Break Dialog");
			dialog.pack();
			dialog.setLocationRelativeTo(frame);
			dialog.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("BreakDialog");
		System.out.println("****************************************");
		System.out.println();
	}
}

