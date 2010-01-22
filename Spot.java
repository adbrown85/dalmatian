/*
 * Spot.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Audio file played during a commercial break.
 */
public class Spot {
	
	private static PreparedStatement deleteStatement,
	                                 insertStatement,
	                                 selectStatement;
	private int id, year;
	private String title, filename, description, sponsor;
	
	
	/**
	 * Static constructor.
	 */
	static {
		
		String message;
		
		try {
			initDeleteStatement();
			initInsertStatement();
			initSelectStatement();
		} catch (SQLException e) {
			message = "[Spot] Could not prepare statements.\n" + 
			          "[Spot] Check database connection.";
			throw new ExceptionInInitializerError(message);
		}
	}
	
	
	public Spot() {
		
		// Initialize
		id = 0;
		description = null;
		filename = null;
		title = null;
		sponsor = null;
		year = 0;
	}
	
	
	public Spot(ResultSet resultSet)
	            throws SQLException {
		
		// Initialize
		id = resultSet.getInt("id");
		description = resultSet.getString("description");
		filename = resultSet.getString("filename");
		title = resultSet.getString("title");
		sponsor = resultSet.getString("sponsor");
		year = resultSet.getInt("year");
	}
	
	
	/**
	 * Creates a new %Spot from its ID in the database.
	 */
	public Spot(int id) 
	            throws SQLException {
		
		ResultSet results;
		
		// Execute query
		selectStatement.setInt(1, id);
		results = selectStatement.executeQuery();
		
		// Process results
		results.next();
		this.id = results.getInt("id");
		this.sponsor = results.getString("sponsor");
		this.title = results.getString("title");
		this.filename = results.getString("filename");
		this.description = results.getString("description");
		this.year = results.getInt("year");
	}
	
	
	public static void delete(int id)
	                          throws SQLException {
		
		// Execute update
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}
	
	
	public String getDescription() {
		
		return description;
	}
	
	
	public String getFilename() {
		
		return filename;
	}
	
	
	public int getId() {
		
		return id;
	}
	
	
	public String getName() {
		
		return title;
	}
	
	
	public String getSponsor() {
		
		return sponsor;
	}
	
	
	public int getYear() {
		
		return year;
	}
	
	
	/**
	 * Initializes the sql statement for delete a spot to the database.
	 */
	private static void initDeleteStatement()
	                                        throws SQLException {
		
		String sql;
		
		// Prepare statement
		sql = "DELETE FROM spot WHERE id=?";
		deleteStatement = Database.prepareStatement(sql);
	}
	
	
	/**
	 * Initializes the sql statement for adding a spot to the database.
	 */
	private static void initInsertStatement()
	                                        throws SQLException {
		
		Connection connection;
		String sql;
		
		// Prepare statement
		connection = Database.getConnection();
		sql = "INSERT INTO spot(sponsor, title, year, filename, description) " +
		      "VALUES(?, ?, ?, ?, ?)";
		insertStatement = connection.prepareStatement(sql);
	}
	
	
	/**
	 * Initializes the select statement used to retrieve a spot.
	 */
	private static void initSelectStatement()
	                                        throws SQLException {
		
		Connection connection;
		String sql;
		
		// Prepare statement
		sql = "SELECT * FROM spot WHERE id = ?";
		connection = Database.getConnection();
		selectStatement = connection.prepareStatement(sql);
	}
	
	
	public void insert()
	                   throws SQLException {
		
		// Execute statement
		insertStatement.setString(1, sponsor);
		insertStatement.setString(2, title);
		insertStatement.setInt(3, year);
		insertStatement.setString(4, filename);
		insertStatement.setString(5, description);
		insertStatement.executeUpdate();
	}
	
	
	public void print() {
		
		System.out.println(toString());
	}
	
	
	public void setDescription(String description) {
		
		this.description = description;
	}
	
	
	public void setFilename(String filename) {
		
		this.filename = filename;
	}
	
	
	public void setSponsor(String sponsor) {
		
		this.sponsor = sponsor;
	}
	
	
	public void setTitle(String title) {
		
		this.title = title;
	}
	
	
	public void setYear(int year) {
		
		this.year = year;
	}
	
	
	public String toString() {
		
		return String.format("\"%s\", %d, \"%s\", \"%s\"",
		                     sponsor, year, title, filename);
	}
	
	
	/**
	 * Tests %Spot.
	 */
	public static void main(String[] args) {
		
		Spot spot;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Spot");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		try {
			spot = new Spot(1);
			spot.print();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Spot");
		System.out.println("****************************************");
		System.out.println();
	}
}

