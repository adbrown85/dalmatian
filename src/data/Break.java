/*
 * Break.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package data;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



/**
 * Time interval between radio programming for playing spots.
 */
public class Break {
	
	private static PreparedStatement deleteStatement;
	private static PreparedStatement insertStatement;
	private static PreparedStatement selectStatement;
	private static PreparedStatement updateStatement;
	private static DateFormat dateFormat;
	
	private int id;                     // Identifier in database
	private Timestamp start;            // Start of the break
	private Timestamp end;              // End of the break
	
	/**
	 * Initializes prepared SQL statements and date format.
	 */
	static {
		try {
			dateFormat = makeDateFormat();
			deleteStatement = makeDeleteStatement();
			insertStatement = makeInsertStatement();
			selectStatement = makeSelectStatement();
			updateStatement = makeUpdateStatement();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("Could not prepare SQL!");
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
	
	/**
	 * Creates a break by copying another break.
	 * 
	 * @param b Another break
	 */
	public Break(Break b) {
		this.id = b.id;
		this.start = b.start;
		this.end = b.end;
	}
	
	/**
	 * Creates a break from a database ID.
	 * 
	 * @param id Identifier of a break in the database
	 * @throws SQLException if ID is not in database
	 */
	public Break(int id) throws SQLException {
		
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
	
	/** 
	 * Deletes a break from the database.
	 * 
	 * @param id Identifier of break in database
	 * @throws SQLException if ID is not in database
	 */
	public static void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}
	
	/**
	 * Inserts a break into the database.
	 * 
	 * @throws SQLException if could not be inserted
	 */
	public void insert() throws SQLException {
		
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
	
	/**
	 * Updates the values of a break.
	 * 
	 * @param original Break with original ID
	 * @param updated Break with new values
	 * @throws SQLException
	 */
   public static void update(Break original,
                             Break updated)
                             throws SQLException {
      updateStatement.setInt(1, updated.id);
      updateStatement.setTimestamp(2, updated.start);
      updateStatement.setTimestamp(3, updated.end);
      updateStatement.setInt(4, original.id);
      updateStatement.executeUpdate();
   }
	
   //------------------------------------------------------------
   // Helpers
   //
   
   private static DateFormat makeDateFormat() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   }
   
   private static PreparedStatement makeDeleteStatement() throws SQLException {
      return Database.prepare(
            "DELETE " +
            "FROM break " +
            "WHERE id=?");
   }
   
   private static PreparedStatement makeInsertStatement() throws SQLException {
      return Database.prepare(
            "INSERT " +
            "INTO break(start,end) " +
            "VALUES(?, ?)");
   }
   
   private static PreparedStatement makeSelectStatement() throws SQLException {
      return Database.prepare(
            "SELECT * " +
            "FROM break " +
            "WHERE id = ?");
   }
   
   private static PreparedStatement makeUpdateStatement() throws SQLException {
      return Database.prepare(
            "UPDATE break " + 
            "SET id=?, start=?, end=? " +
            "WHERE id=?");
   }
   
   //------------------------------------------------------------
   // Getters and setters
   //
   
   public Timestamp getEnd() {
      return end;
   }
   
   public int getId() {
      return id;
   }
   
   public Timestamp getStart() {
      return start;
   }
	
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setStart(Timestamp start) {
		this.start = start;
	}
	
	//------------------------------------------------------------
   // Converters
   //
	
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
			id = _break.getId();
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

