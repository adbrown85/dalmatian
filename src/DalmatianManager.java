/*
 * Manager.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.sql.SQLException;


/**
 * Application for managing breaks, spots, and sponsors.
 */
public class DalmatianManager {
	
   private static final String TITLE = "Dalmatian Manager";
   private final JFrame frame;
	
	public DalmatianManager() throws SQLException {
		this.frame = makeFrame();
	}
	
	public void run() {
		frame.pack();
		frame.setVisible(true);
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
	private static JFrame makeFrame() throws SQLException {
	   
	   JFrame frame = new JFrame(TITLE);
	   JTabbedPane tp = new JTabbedPane();
	   
	   tp.addTab("Breaks", new BreakManager(frame));
	   tp.addTab("Spots", new SpotManager(frame));
	   tp.addTab("Sponsors", new SponsorManager(frame));
	   
	   frame.setContentPane(tp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		return frame;
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) throws SQLException {
		
		DalmatianManager dm = new DalmatianManager();
		
		dm.run();
	}
}

