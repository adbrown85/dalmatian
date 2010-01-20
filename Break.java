/*
 * Break.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.*;
import java.text.SimpleDateFormat;



/**
 * Time interval between radio programming for playing spots.
 */
public class Break {
	
	private static PreparedStatement selectStatement;
	private static SimpleDateFormat dateFormat;
	private int id;
	private Timestamp start, end;
	
	
	/**
	 * Initializes prepared SQL statements and date format.
	 */
	static {
		
		String message;
		
		try {
			initDateFormat();
			initSelectStatement();
		} catch (SQLException e) {
			message = "[Break] Could not prepare SQL statements.\n" + 
			          "[Break] Check database connection.";
			throw new ExceptionInInitializerError(message);
		}
	}
	
	
	/**
	 * Creates a new empty break.
	 */
	public Break() {
		
		this.id = 0;
		this.start = null;
		this.end = null;
	}
	
	
	public Break(int id)
	             throws SQLException {
		
		ResultSet results;
		
		// Execute query
		selectStatement.setInt(1, id);
		results = selectStatement.executeQuery();
		
		// Process results
		results.next();
		this.id = results.getInt("id");
		this.start = results.getTimestamp("start");
		this.end = results.getTimestamp("end");
	}
	
	
	private static void initDateFormat() {
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	private static void initSelectStatement()
	                                        throws SQLException {
		
		String sql;
		
		// Prepare statement
		sql = "SELECT * FROM break WHERE id = ?";
		selectStatement = Database.prepareStatement(sql);
	}
	
	
	public void print() {
		
		System.out.println(toString());
	}
	
	
	public void setEnd(Timestamp end) {
		
		this.end = end;
	}
	
	
	public void setStart(Timestamp start) {
		
		this.start = start;
	}
	
	
	public String toString() {
		
		return String.format("%d: %s, %s", id,
		                     dateFormat.format(start),
		                     dateFormat.format(end));
	}
	
	
	/**
	 * Test for Break.
	 */
	public static void main(String[] args) {
		
		Break aBreak;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Break");
		System.out.println("****************************************");
		System.out.println();
		
		// Test retrieval
		try {
			aBreak = new Break(1);
			aBreak.print();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Break");
		System.out.println("****************************************");
		System.out.println();
	}
}

