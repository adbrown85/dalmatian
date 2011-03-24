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
	
	private Calendar calendar;
	private JComboBox monthCombo;
	private JComboBox dayCombo;
	private JComboBox yearCombo;
	private JComboBox hourCombo;
	private JComboBox minuteCombo;
	private JComboBox secondCombo;
	private JComboBox amPmCombo;
	private JPanel datePanel;
	private JPanel timePanel;
	private Timestamp timestamp;
	
	/**
	 * Creates a new TimestampEditor.
	 */
	public TimestampEditor(Timestamp timestamp) throws SQLException {
		
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		initDatePanel();
		initTimePanel();
		initTimestamp(timestamp);
		initCalendar();
		initDate();
		initTime();
	}
	
	private void initCalendar() {
		calendar = new GregorianCalendar();
		calendar.setTime(this.timestamp);
	}
	
	private void initDate() {
		monthCombo.setSelectedItem(calendar.get(Calendar.MONTH) + 1);
		dayCombo.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
		yearCombo.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	private void initDatePanel() {
		
		// Create and add panel
		datePanel = new JPanel();
		add(datePanel);
		
		// Add months
		monthCombo = GUI.getComboBoxFor(1, 12);
		datePanel.add(monthCombo);
		
		// Add days
		dayCombo = GUI.getComboBoxFor(1, 31);
		datePanel.add(dayCombo);
		
		// Add years
		yearCombo = GUI.getComboBoxFor(2010, 2035);
		datePanel.add(yearCombo);
	}
	
	private void initTime() {
		
		// Initialize to calendar
		hourCombo.setSelectedIndex(calendar.get(Calendar.HOUR));
		minuteCombo.setSelectedItem(calendar.get(Calendar.MINUTE));
		// secondCombo.setSelectedItem(calendar.get(Calendar.SECOND));
		if (calendar.get(Calendar.AM_PM) == Calendar.AM)
			amPmCombo.setSelectedItem("AM");
		else
			amPmCombo.setSelectedItem("PM");
	}
	
	private void initTimePanel() {
		
		// Create and add panel
		timePanel = new JPanel();
		add(timePanel);
		
		// Add hour
		hourCombo = new JComboBox();
		hourCombo.addItem(12);
		for (int i=1; i<=11; ++i)
			hourCombo.addItem(i);
		timePanel.add(hourCombo);
		
		// Add minute
		minuteCombo = GUI.getComboBoxFor(0, 59);
		timePanel.add(minuteCombo);
		
		// Add second
		secondCombo = GUI.getComboBoxFor(0, 59);
		timePanel.add(secondCombo);
		
		// Add AM/PM
		amPmCombo = new JComboBox();
		amPmCombo.addItem("AM");
		amPmCombo.addItem("PM");
		timePanel.add(amPmCombo);
	}
	
	private void initTimestamp(Timestamp timestamp) throws SQLException {
		if (timestamp == null)
			this.timestamp = Database.getCurrentTimestamp();
		else
			this.timestamp = timestamp;
	}
	
	//------------------------------------------------------------
   // Getters and setters
   //
	
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
	
	public void setTimestamp(Timestamp timestamp) throws SQLException {
		initTimestamp(timestamp);
		initCalendar();
		initDate();
		initTime();
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws Exception {
		
		final JButton button = new JButton("Print");
		final JFrame frame = new JFrame("TimestampEditor");
		final JPanel pane = new JPanel();
		final TimestampEditor te = new TimestampEditor(null);
		
		// Make content pane with editor
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		pane.add(te);
		
		// Add button
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println(te.getTimestamp());
			}
		});
		pane.add(button);
		
		// Create and show frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(pane);
		frame.pack();
		frame.setVisible(true);
	}
}

