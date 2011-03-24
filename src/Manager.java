/*
 * Manager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.sql.SQLException;



/**
 * Dalmatian Manager.
 */
public class Manager {
	
	private JTabbedPane tabbedPane;
	private JFrame frame;
	
	public Manager() throws SQLException {
		
		// Make frame
		frame = new JFrame("Dalmatian Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		// Make pane and add to frame
		tabbedPane = new JTabbedPane();
		initTabs();
		frame.setContentPane(tabbedPane);
	}
	
	public void initTabs() throws SQLException {
		
		JPanel breaksPanel, sponsorsPanel, spotsPanel;
		
		// Breaks
		breaksPanel = new BreakManager(frame);
		tabbedPane.addTab("Breaks", breaksPanel);
		
		// Spots
		spotsPanel = new SpotManager(frame);
		tabbedPane.addTab("Spots", spotsPanel);
		
		// Sponsors
		sponsorsPanel = new SponsorManager(frame);
		tabbedPane.addTab("Sponsors", sponsorsPanel);
	}
	
	public void run() {
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		Manager manager;
		
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Manager");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			manager = new Manager();
			manager.run();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Manager");
		System.out.println("****************************************");
		System.out.println();
	}
}

