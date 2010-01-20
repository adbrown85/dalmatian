/*
 * TimestampEditor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;



/**
 * GUI for editing a timestamp.
 */
public class TimestampEditor extends JPanel {
	
	Calendar calendar;
	JComboBox monthCombo, dayCombo, yearCombo;
	JComboBox hourCombo, minuteCombo, secondCombo, amPmCombo;
	JPanel datePanel, timePanel;
	Timestamp timestamp;
	
	
	/**
	 * Creates a new %TimestampEditor.
	 */
	public TimestampEditor(Timestamp timestamp) 
	                       throws SQLException {
		
		// Initialize
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		initTimestamp(timestamp);
		initCalendar();
		initDatePanel();
		initTimePanel();
	}
	
	
	public Timestamp getTimestamp() {
		
		int month, day, year;
		int hour, minute, second;
		String amPm;
		
		// Get date
		month = monthCombo.getSelectedIndex();
		day = (Integer)dayCombo.getSelectedItem();
		year = (Integer)yearCombo.getSelectedItem();
		
		// Get time
		hour = (Integer)hourCombo.getSelectedItem();
		if (hour == 12)
			hour = 0;
		minute = (Integer)minuteCombo.getSelectedItem();
		second = (Integer)secondCombo.getSelectedItem();
		amPm = (String)amPmCombo.getSelectedItem();
		if (amPm.equals("PM"))
			hour += 12;
		
		// Set calendar and return timestamp
		calendar.set(year, month, day, hour, minute, second);
		return new Timestamp(calendar.getTimeInMillis());
	}
	
	
	private void initCalendar() {
		
		// Set calendar from timestamp
		calendar = new GregorianCalendar();
		calendar.setTime(this.timestamp);
	}
	
	
	private JComboBox initComboBoxWith(int start,
	                                   int end) {
		
		JComboBox comboBox;
		Integer[] range;
		
		// Make range
		comboBox = new JComboBox();
		for (int i=start; i<=end; ++i)
			comboBox.addItem(i);
		return comboBox;
	}
	
	
	private void initDatePanel() {
		
		// Create and add panel
		datePanel = new JPanel();
		add(datePanel);
		
		// Add months
		monthCombo = initComboBoxWith(1, 12);
		datePanel.add(monthCombo);
		monthCombo.setSelectedItem(calendar.get(Calendar.MONTH) + 1);
		
		// Add days
		dayCombo = initComboBoxWith(1, 31);
		datePanel.add(dayCombo);
		dayCombo.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
		
		// Add years
		yearCombo = initComboBoxWith(2010, 2035);
		datePanel.add(yearCombo);
		yearCombo.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	
	private void initTimePanel() {
		
		int hour;
		
		// Create and add panel
		timePanel = new JPanel();
		add(timePanel);
		
		// Add hour
		hourCombo = new JComboBox();
		hourCombo.addItem(12);
		for (int i=1; i<=11; ++i)
			hourCombo.addItem(i);
		timePanel.add(hourCombo);
		hourCombo.setSelectedIndex(calendar.get(Calendar.HOUR));
		
		// Add minute
		minuteCombo = initComboBoxWith(0, 59);
		timePanel.add(minuteCombo);
		minuteCombo.setSelectedItem(calendar.get(Calendar.MINUTE));
		
		// Add second
		secondCombo = initComboBoxWith(0, 59);
		timePanel.add(secondCombo);
		// secondCombo.setSelectedItem(calendar.get(Calendar.SECOND));
		
		// Add AM/PM
		amPmCombo = new JComboBox();
		amPmCombo.addItem("AM");
		amPmCombo.addItem("PM");
		timePanel.add(amPmCombo);
		if (calendar.get(Calendar.AM_PM) == Calendar.PM)
			amPmCombo.setSelectedItem("PM");
	}
	
	
	private void initTimestamp(Timestamp timestamp)
	                           throws SQLException {
		
		// Get from database
		if (timestamp == null)
			this.timestamp = Database.getCurrentTimestamp();
		else
			this.timestamp = timestamp;
	}
	
	
	/**
	 * Test for TimestampEditor.
	 */
	public static void main(String[] args) {
		
		JButton button;
		JFrame frame;
		JPanel pane;
		final TimestampEditor timestampEditor;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("TimestampEditor");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Make content pane with editor
			pane = new JPanel();
			pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
			timestampEditor = new TimestampEditor(null);
			pane.add(timestampEditor);
			
			// Add button
			button = new JButton("Print");
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					System.out.println(timestampEditor.getTimestamp());
				}
			});
			pane.add(button);
			
			// Create and show frame
			frame = new JFrame("TimestampEditor");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(pane);
			frame.pack();
			frame.setVisible(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("TimestampEditor");
		System.out.println("****************************************");
		System.out.println();
	}
}

