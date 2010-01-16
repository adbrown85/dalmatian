/*
 * SponsorManager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import java.sql.SQLException;



/**
 * Manages sponsors.
 */
public class SponsorManager extends JPanel
                            implements ActionListener {
	
	
	private final int BORDER=4, WIDTH=400, HEIGHT=200;
	private JScrollPane scrollPane;
	private JTable table;
	
	
	
	/**
	 * Creates a new %SponsorsManager.
	 */
	public SponsorManager()
	                      throws SQLException {
		
		// Create a panel with a box layout
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		
		// Initialize components
		initTable();
		initButtons();
	}
	
	
	
	/**
	 * Triggered when an action is fired.
	 */
	public void actionPerformed(ActionEvent event) {
		
		int row;
		
		System.out.println(event.getActionCommand());
		row = table.getSelectedRow();
		if (row != -1)
			System.out.printf("It's row %d!\n", row);
	}
	
	
	
	/**
	 * Initializes the buttons.
	 */
	private void initButtons() {
		
		JButton button;
		JPanel panel;
		String[] buttonNames={"New", "Edit", "Delete"};
		
		// Add buttons to panel
		panel = new JPanel();
		for (int i=0; i<buttonNames.length; ++i) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.addActionListener(this);
			panel.add(button);
		}
		
		// Add the panel
		add(panel);
	}
	
	
	
	/**
	 * Initializes the table.
	 */
	private void initTable()
	                       throws SQLException {
		
		double columnWidths[]={0.24, 0.28, 0.12, 0.10, 0.10};
		TableColumn column;
		
		// Create table
		table = new JTable(new SponsorTableModel());
		table.setFillsViewportHeight(true);
		for (int i=0; i<5; ++i) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth((int)(WIDTH * columnWidths[i]));
		}
		
		// Add it with a scroll pane
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}
	
	
	
	/**
	 * Test for %SponsorManager.
	 */
	public static void main(String[] args) {
		
		JFrame frame;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorManager");
		System.out.println("****************************************");
		System.out.println();
		
		// Create frame
		try {
			frame = new JFrame("SponsorManager");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new SponsorManager());
			frame.pack();
			frame.setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("SponsorManager");
		System.out.println("****************************************");
		System.out.println();
	}
}

