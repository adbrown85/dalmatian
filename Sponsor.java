/*
 * Sponsor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Vector;



/**
 * Company or organization that pays for a spot.
 */
public class Sponsor {
	
	private static PreparedStatement deleteStatement,
	                                 insertStatement,
	                                 selectStatement;
	private String name;
	private String street, city, state, zip;
	private String phone;
	
	
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
			message = "[Sponsor] Could not prepare statements.\n" +
			          "[Sponsor] Check database connection.";
			throw new ExceptionInInitializerError(message);
		}
	}
	
	
	/**
	 * Creates a new, empty %Sponsor.
	 */
	public Sponsor() {
		
		// Initialize fields
		name = null;
		street = null;
		city = null;
		state = null;
		zip = null;
		phone = null;
	}
	
	
	/**
	 * Creates a new %Sponsor from one already in the database.
	 */
	public Sponsor(String name)
	               throws SQLException {
		
		ResultSet results;
		
		// Execute query
		selectStatement.setString(1, name);
		results = selectStatement.executeQuery();
		
		// Process results
		results.next();
		this.name = results.getString("name");
		this.street = results.getString("street");
		this.city = results.getString("city");
		this.state = results.getString("state");
		this.zip = results.getString("zip");
		this.phone = results.getString("phone");
	}
	
	
	/**
	 * Creates a new %Sponsor by copying another.
	 */
	public Sponsor(Sponsor other) {
		
		// Copy attibutes
		this.name = other.name;
		this.street = other.street;
		this.city = other.city;
		this.state = other.state;
		this.zip = other.zip;
		this.phone = other.phone;
	}
	
	
	/**
	 * Removes a sponsor from the database.
	 */
	public static void delete(String name)
	                          throws SQLException {
		
		int result;
		
		// Execute update
		deleteStatement.setString(1, name);
		result = deleteStatement.executeUpdate();
		
		// Check results
		if (result != 1)
			throw new SQLException("[Sponsor] No row was deleted.");
	}
	
	
	public static Vector<String> getFieldNames() {
		
		Vector<String> fieldNames;
		
		fieldNames = new Vector<String>();
		fieldNames.add("Name");
		fieldNames.add("Street");
		fieldNames.add("City");
		fieldNames.add("State");
		fieldNames.add("Zip");
		fieldNames.add("Phone");
		return fieldNames;
	}
	
	
	public static Vector<String> getAllNames() 
	                                         throws SQLException {
		
		ResultSet results;
		String sql;
		Vector<String> names;
		
		// Execute query
		sql = "SELECT name FROM sponsor ORDER BY name";
		results = Database.executeQuery(sql);
		
		// Process results
		names = new Vector<String>();
		while (results.next()) {
			names.add(results.getString(1));
		}
		return names;
	}
	
	
	public String getName() {return name;}
	
	public String getStreet() {return street;}
	
	public String getCity() {return city;}
	
	public String getState() {return state;}
	
	public String getZip() {return zip;}
	
	public String getPhone() {return phone;}
	
	
	private static void initDeleteStatement()
	                                        throws SQLException {
		
		Connection connection;
		String sql;
		
		// Form and prepare statement
		connection = Database.getConnection();
		sql = "DELETE FROM sponsor WHERE name = ? LIMIT 1";
		deleteStatement = connection.prepareStatement(sql);
	}
	
	
	private static void initInsertStatement()
	                                        throws SQLException {
		
		Connection connection;
		String sql;
		
		// Form and prepare statement
		connection = Database.getConnection();
		sql = "INSERT INTO sponsor(name, street, city, state, zip, phone)"
		      + " VALUES(?, ?, ?, ?, ?, ?)";
		insertStatement = connection.prepareStatement(sql);
	}
	
	
	private static void initSelectStatement()
	                                        throws SQLException {
		
		Connection connection;
		String sql;
		
		// Form and prepare statement
		connection = Database.getConnection();
		sql = "SELECT * FROM sponsor WHERE name = ?";
		selectStatement = connection.prepareStatement(sql);
	}
	
	
	/**
	 * Inserts the sponsor into the database.
	 */
	public void insert()
	                   throws SQLException {
		
		int result;
		Statement statement;
		String sql;
		
		// Form and execute statement
		insertStatement.setString(1, name);
		insertStatement.setString(2, street);
		insertStatement.setString(3, city);
		insertStatement.setString(4, state);
		insertStatement.setString(5, zip);
		insertStatement.setString(6, phone);
		result = insertStatement.executeUpdate();
		
		// Check results
		if (result == 0)
			throw new SQLException("[Sponsor] No row was inserted.");
	}
	
	
	public void print() {
		
		System.out.println(toString());
	}
	
	
	public void setName(String name) {this.name = name;}
	
	public void setStreet(String street) {this.street = street;}
	
	public void setCity(String city) {this.city = city;}
	
	public void setState(String state) {this.state = state;}
	
	public void setZip(String zip) {this.zip = zip;}
	
	public void setPhone(String phone) {this.phone = phone;}
	
	
	public String toString() {
		
		return String.format("%s, %s, %s, %s, %s, %s",
		                     name, street, city, state, zip, phone);
	}
	
	
	/**
	 * Test for %Sponsor.
	 */
	public static void main(String[] args) {
		
		Sponsor sponsor;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Sponsor");
		System.out.println("****************************************");
		System.out.println();
		
		// Test insert
		try {
			sponsor = new Sponsor();
			sponsor.setName("Guthrie");
			sponsor.setStreet("1 Guthrie Sq");
			sponsor.setCity("Sayre");
			sponsor.setState("PA");
			sponsor.setZip("18840");
			sponsor.setPhone("5708886666");
			sponsor.insert();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		// Test select
		try {
			sponsor = new Sponsor("Guthrie");
			sponsor.print();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Test delete
		try {
			Sponsor.delete("Guthrie");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Test retrieval
		try {
			Vector<String> names = Sponsor.getAllNames();
			for (String name : names) {
				System.out.println(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Sponsor");
		System.out.println("****************************************");
		System.out.println();
	}
}

