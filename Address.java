/*
 * Address.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Stores a mailing address.
 */
public class Address {
	
	private String street, city, state, zip;
	
	
	
	/**
	 * Creates an %Address from one already in the database.
	 * 
	 * @param id
	 *     ID of the %Address in the database.
	 */
	public Address(int id)
	               throws IOException {
		
		ResultSet results;
		String query;
		
		try {
			
			// Execute query
			query = String.format("SELECT * FROM address WHERE id=%d", id);
			results = Database.executeQuery(query);
			if (!results.next())
				throw new IOException("[Address] Address not in database.");
			
			// Process results
			street = results.getString("street");
			city = results.getString("city");
			state = results.getString("state");
			zip = results.getString("zip");
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	/**
	 * Creates a copy of another %Address.
	 */
	public Address(Address other) {
		
		// Copy attributes
		this.street = other.street;
		this.city = other.city;
		this.state = other.state;
		this.zip = other.zip;
	}
	
	
	
	public String getCity() {
		
		return new String(city);
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
	 * Prints the %Address.
	 */
	public void print() {
		
		System.out.println(toString());
	}
	
	
	
	/**
	 * Formats the %Address's attributes as a string.
	 */
	public String toString() {
		
		return String.format("%s %s %s %s", street, city, state, zip);
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
		
		// Test
		try {
			address = new Address(1);
			address.print();
			System.out.println(address.getStreet());
			System.out.println(address.getCity());
			System.out.println(address.getState());
			System.out.println(address.getZip());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Address");
		System.out.println("****************************************");
		System.out.println();
	}
}

