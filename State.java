/*
 * State.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Utility for retrieving states from database.
 */
public class State {
	
	private static String[] codes;
	
	
	static {
		
		try {
			initCodes();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("[State] Check database.");
		}
	}
	
	
	private static void initCodes()
	                              throws SQLException {
		
		int count, index=-1;
		ResultSet results;
		String sql;
		
		// Count states in database
		sql = "SELECT COUNT(code) FROM state";
		results = Database.executeQuery(sql);
		results.next();
		count = results.getInt(1);
		
		// Execute query and process results
		sql = "SELECT code FROM state";
		results = Database.executeQuery(sql);
		codes = new String[count];
		while (results.next()) {
			codes[++index] = results.getString(1);
		}
	}
	
	
	public static String[] getCodes() {
		
		return codes;
	}
	
	
	public static void main(String[] args) {
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("State");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		for (String code : getCodes()) {
			System.out.println(code);
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("State");
		System.out.println("****************************************");
		System.out.println();
	}
}

