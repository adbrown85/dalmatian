/*
 * Manager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.sql.SQLException;



/**
 * Dalmatian %Manager.
 */
public class Manager {
	
	
	JTabbedPane tabbedPane;
	JFrame frame;
	
	
	
	/**
	 * Creates a new %Manager.
	 */
	public Manager()
	               throws SQLException {
		
		// Make frame
		frame = new JFrame("Dalmatian Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		// Make pane and add to frame
		tabbedPane = new JTabbedPane();
		initTabs();
		frame.setContentPane(tabbedPane);
	}
	
	
	
	/**
	 * Initializes all the tabs.
	 */
	public void initTabs()
	                     throws SQLException {
		
		JPanel breaksPanel, sponsorsPanel, spotsPanel;
		
		// Sponsors
		sponsorsPanel = new SponsorManager();
		tabbedPane.addTab("Sponsors", sponsorsPanel);
		
		// Spots
		spotsPanel = new JPanel();
		tabbedPane.addTab("Spots", spotsPanel);
		
		// Breaks
		breaksPanel = new JPanel();
		tabbedPane.addTab("Breaks", breaksPanel);
	}
	
	
	
	/**
	 * Starts the %Manager.
	 */
	public void run() {
		
		// Pack and show frame
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	/**
	 * Entry point for %Manager.
	 */
	public static void main(String[] args) {
		
		Manager manager;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Manager");
		System.out.println("****************************************");
		System.out.println();
		
		// Run
		try {
			manager = new Manager();
			manager.run();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Manager");
		System.out.println("****************************************");
		System.out.println();
	}
}

