/*
 * Address.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * Stores a mailing address.
 */
public class Address {
	
	private int id;
	private String street, city, state, zip;
	
	
	/**
	 * Creates a new %Address not already in the database.
	 */
	public Address() {
		
		// Initialize
		id = 0;
		street = null;
		city = null;
		state = null;
		zip = null;
	}
	
	
	/**
	 * Creates an %Address from one already in the database.
	 * 
	 * @param id
	 *     ID of the %Address in the database.
	 */
	public Address(int id)
	               throws SQLException {
		
		ResultSet results;
		String sql;
		
		// Execute query
		sql = String.format("SELECT * FROM address WHERE id=%d", id);
		results = Database.executeQuery(sql);
		
		// Process results
		results.next();
		street = results.getString("street");
		city = results.getString("city");
		state = results.getString("state");
		zip = results.getString("zip");
	}
	
	
	/**
	 * Creates a copy of another %Address.
	 */
	public Address(Address other) {
		
		// Copy attributes
		this.id = other.id;
		this.street = other.street;
		this.city = other.city;
		this.state = other.state;
		this.zip = other.zip;
	}
	
	
	public String getCity() {
		
		return new String(city);
	}
	
	
	public int getID() {
		
		return id;
	}
	
	
	public String getState() {
		
		return new String(state);
	}
	
	
	public String getStreet() {
		
		return new String(street);
	}
	
	
	public String getZip() {
		
		return new String(zip);
	}
	
	
	/**
	 * Inserts the %Address into the database.
	 */
	public void insert()
	                   throws SQLException {
		
		ResultSet keys;
		Statement statement;
		String sql;
		
		// Execute update
		sql = "INSERT INTO address(street, city, state, zip)"
		       + " VALUES("
		       + SQL.format(street)
		       + ", " + SQL.format(city)
		       + ", " + SQL.format(state)
		       + ", " + SQL.format(zip)
		       + ")";
		statement = Database.getNewStatement();
		statement.executeUpdate(sql);
		
		// Retrieve ID
		keys = statement.getGeneratedKeys();
		keys.next();
		id = keys.getInt(1);
	}
	
	
	/**
	 * Prints the %Address.
	 */
	public void print() {
		
		System.out.println(toString());
	}
	
	
	public void setCity(String city) {
		
		this.city = city;
	}
	
	
	public void setState(String state) {
		
		this.state = state;
	}
	
	
	public void setStreet(String street) {
		
		this.street = street;
	}
	
	
	public void setZip(String zip) {
		
		this.zip = zip;
	}
	
	
	/** 
	 * Formats the %Address's attributes as a string.
	 */
	public String toString() {
		
		return String.format("%d: %s %s %s %s", id, street, city, state, zip);
	}
	
	
	/**
	 * Test for %Address.
	 */
	public static void main(String[] args) {
		
		Address address;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Address");
		System.out.println("****************************************");
		System.out.println();
		
		// Test retrieval
		try {
			address = new Address(1);
			address.print();
			System.out.println(address.getStreet());
			System.out.println(address.getCity());
			System.out.println(address.getState());
			System.out.println(address.getZip());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Test insert with null
		try {
			address = new Address();
			address.setStreet("1200 Park Ave");
			address.setCity("Emeryville");
			address.setState("CA");
			address.insert();
			address.print();
		} catch (SQLException e) {
			System.err.println("[Address] " + e.getMessage());
		}
		
		// Test insert without null
		try {
			address = new Address();
			address.setStreet("1800 Willow Dr");
			address.setCity("Easton");
			address.setState("PA");
			address.setZip("18040");
			address.insert();
			address.print();
		} catch (SQLException e) {
			System.err.println("[Address] " + e.getMessage());
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Address");
		System.out.println("****************************************");
		System.out.println();
	}
}

