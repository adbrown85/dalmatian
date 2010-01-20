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
	
	private static PreparedStatement deleteStatement,
	                                 insertStatement,
	                                 selectStatement;
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
			initDeleteStatement();
			initInsertStatement();
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
	
	
	public Timestamp getEnd() {
		
		return end;
	}
	
	
	public int getID() {
		
		return id;
	}
	
	
	public Timestamp getStart() {
		
		return start;
	}
	
	
	public static void delete(int id)
	                          throws SQLException {
		
		// Execute update
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}
	
	
	private static void initDateFormat() {
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	
	private static void initDeleteStatement()
	                                        throws SQLException {
		
		String sql;
		
		// Prepare statement
		sql = "DELETE FROM break WHERE id=?";
		deleteStatement = Database.prepareStatement(sql);
	}
	
	
	private static void initInsertStatement()
	                                        throws SQLException {
		
		String sql;
		
		// Prepare statement
		sql = "INSERT INTO break(start,end) " +
		      "VALUES(?, ?)";
		insertStatement = Database.prepareStatement(sql);
	}
	
	
	private static void initSelectStatement()
	                                        throws SQLException {
		
		String sql;
		
		// Prepare statement
		sql = "SELECT * FROM break WHERE id = ?";
		selectStatement = Database.prepareStatement(sql);
	}
	
	
	public void insert()
	                   throws SQLException {
		
		ResultSet keys;
		
		// Execute update
		insertStatement.setTimestamp(1, start);
		insertStatement.setTimestamp(2, end);
		insertStatement.executeUpdate();
		
		// Get generated key
		keys = insertStatement.getGeneratedKeys();
		keys.next();
		this.id = keys.getInt(1);
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
		
		Break _break;
		int id;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Break");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Test insert
			_break = new Break();
			_break.setStart(Timestamp.valueOf("2010-01-20 22:00:00"));
			_break.setEnd(Timestamp.valueOf("2010-01-20 22:04:00"));
			_break.insert();
			id = _break.getID();
			System.out.printf("Inserted id: %d\n", id);
			
			// Test retrieval
			_break = new Break(id);
			_break.print();
			
			// Test delete
			Break.delete(id);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Break");
		System.out.println("****************************************");
		System.out.println();
	}
}

