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
			codes = makeCodes();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("[State] Check database.");
		}
	}
	
	private static String[] makeCodes() throws SQLException {
		
		int count;
		int index = -1;
		String sql;
		ResultSet rs;
		String[] codes;
		
		// Count states in database
		sql = "SELECT COUNT(code) FROM state";
		rs = Database.executeQuery(sql);
		rs.next();
		count = rs.getInt(1);
		
		// Execute query and process results
		sql = "SELECT code FROM state";
		rs = Database.executeQuery(sql);
		codes = new String[count];
		while (rs.next()) {
			codes[++index] = rs.getString(1);
		}
		
		return codes;
	}
	
	public static String[] getCodes() {
		return codes;
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		for (String code : getCodes()) {
			System.out.println(code);
		}
	}
}

