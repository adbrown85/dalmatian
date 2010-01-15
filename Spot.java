/*
 * Spot.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * Audio file played during a commercial break.
 */
public class Spot {
	
	private int id, year;
	private String name, filename, description;
	private Sponsor sponsor;
	
	
	
	/**
	 * Creates a new %Spot from its ID in the database.
	 */
	public Spot(int id) 
	            throws IOException {
		
		ResultSet rs;
		String query;
		
		try {
			
			// Execute query
			query = String.format("SELECT * FROM spot WHERE id=%d", id);
			rs = Database.executeQuery(query);
			if (!rs.next())
				throw new IOException("[Spot] Spot not in database.");
			
			// Process results
			this.id = rs.getInt("id");
			this.sponsor = new Sponsor(rs.getInt("sponsor"));
			this.name = rs.getString("name");
			this.filename = rs.getString("filename");
			this.description = rs.getString("description");
			this.year = rs.getInt("year");
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	public String getDescription() {
		
		return description;
	}
	
	
	
	public String getFilename() {
		
		return filename;
	}
	
	
	
	public String getName() {
		
		return name;
	}
	
	
	
	public Sponsor getSponsor() {
		
		return new Sponsor(sponsor);
	}
	
	
	
	public int getYear() {
		
		return year;
	}
	
	
	
	/**
	 * Prints the %Spot.
	 */
	public void print() {
		
		System.out.println(toString());
	}
	
	
	
	/**
	 * Formats the %Spot's attributes as a string.
	 */
	public String toString() {
		
		return String.format("\"%s\" %d \"%s\"", sponsor.getName(), year, name);
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
		} catch (IOException e) {
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

