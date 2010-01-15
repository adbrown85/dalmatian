/*
 * Sponsor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Company or organization that pays for a spot.
 */
public class Sponsor {
	
	
	private Address address;
	private String name;
	
	
	
	/**
	 * Creates a new %Sponsor from one already in the database.
	 */
	public Sponsor(int id)
	               throws IOException {
		
		ResultSet results;
		String query;
		
		try {
			
			// Execute query
			query = String.format("SELECT * FROM sponsor WHERE id=%d", id);
			results = Database.executeQuery(query);
			if (!results.next())
				throw new IOException("[Sponsor] Sponsor not in database.");
			
			// Process results
			name = results.getString("name");
			address = new Address(results.getInt("address"));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Creates a new %Sponsor by copying another.
	 */
	public Sponsor(Sponsor other) {
		
		this.address = new Address(other.address);
		this.name = other.name;
	}
	
	
	
	public Address getAddress() {
		
		return new Address(address);
	}
	
	
	
	public String getName() {
		
		return name;
	}
	
	
	/**
	 * Prints the %Sponsor's attributes.
	 */
	public void print() {
		
		System.out.println(toString());
	}
	
	
	
	/**
	 * Formats the %Sponsor's attributes as a string
	 */
	public String toString() {
		
		return String.format("%s", name);
	}
	
	
	
	/**
	 * Test for %Sponsor.
	 */
	public static void main(String[] args) {
		
		Address address;
		Sponsor sponsor;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Sponsor");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			// Test
			sponsor = new Sponsor(1);
			sponsor.print();
			address = sponsor.getAddress();
			address.print();
		}
		catch (IOException e) {
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

