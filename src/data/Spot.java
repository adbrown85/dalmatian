package data;
/*
 * Spot.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Audio file played during a commercial break.
 */
public class Spot {
	
	private static final PreparedStatement deleteStatement;
	private static final PreparedStatement insertStatement;
	private static final PreparedStatement selectStatement;
	private static final PreparedStatement updateStatement;
	
	private int id, year;
	private String title, filename, description, sponsor;
	
	/**
	 * Initializes static fields.
	 */
	static {
		try {
			deleteStatement = makeDeleteStatement();
			insertStatement = makeInsertStatement();
			selectStatement = makeSelectStatement();
			updateStatement = makeUpdateStatement();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError("Could not make statements!");
		}
	}
	
	public Spot() {
		id = 0;
		description = null;
		filename = null;
		title = null;
		sponsor = null;
		year = 0;
	}
	
	public Spot(Spot other) {
		id = other.id;
		sponsor = other.sponsor;
		title = other.title;
		year = other.year;
		filename = other.filename;
		description = other.description;
	}
	
	public Spot(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
		description = rs.getString("description");
		filename = rs.getString("filename");
		title = rs.getString("title");
		sponsor = rs.getString("sponsor");
		year = rs.getInt("year");
	}
	
	/**
	 * Creates a new Spot from its ID in the database.
	 */
	public Spot(int id)  throws SQLException {
		
		ResultSet rs;
		
		// Execute query
		selectStatement.setInt(1, id);
		rs = selectStatement.executeQuery();
		
		// Process results
		rs.next();
		this.id = rs.getInt("id");
		this.sponsor = rs.getString("sponsor");
		this.title = rs.getString("title");
		this.filename = rs.getString("filename");
		this.description = rs.getString("description");
		this.year = rs.getInt("year");
	}
	
	public static void delete(int id) throws SQLException {
		deleteStatement.setInt(1, id);
		deleteStatement.executeUpdate();
	}
	
   public void insert() throws SQLException {
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
   
   public static void update(Spot original,
                             Spot updated)
                             throws SQLException {
      updateStatement.setString(1, updated.sponsor);
      updateStatement.setString(2, updated.title);
      updateStatement.setInt(3, updated.year);
      updateStatement.setString(4, updated.filename);
      updateStatement.setString(5, updated.description);
      updateStatement.setInt(6, original.id);
      updateStatement.executeUpdate();
   }
   
   //------------------------------------------------------------
   // Helpers
   //
   
	private static PreparedStatement makeDeleteStatement() throws SQLException {
		return Database.prepare(
		      "DELETE " +
		      "FROM spot " +
		      "WHERE id=?");
	}
	
	private static PreparedStatement makeInsertStatement() throws SQLException {
		return Database.prepare(
		      "INSERT INTO spot(sponsor, title, year, filename, description) " +
            "VALUES(?, ?, ?, ?, ?)");
	}
	
	private static PreparedStatement makeSelectStatement() throws SQLException {
		return Database.prepare(
		      "SELECT * " +
		      "FROM spot " +
		      "WHERE id = ?");
	}
	
	private static PreparedStatement makeUpdateStatement() throws SQLException {
		return Database.prepare(
		      "UPDATE spot " +
            "SET sponsor=?, title=?, year=?, filename=?, description=? " + 
            "WHERE id=?");
	}
	
	//------------------------------------------------------------
   // Getters and setters
   //
	
   public String getDescription() {
      return description;
   }
   
   public void setDescription(String description) {
      this.description = description;
   }
   
   public String getFilename() {
      return filename;
   }
   
   public void setFilename(String filename) {
      this.filename = filename;
   }
   
   public int getId() {
      return id;
   }
   
   public String getSponsor() {
      return sponsor;
   }
   
   public void setSponsor(String sponsor) {
      this.sponsor = sponsor;
   }
   
   public String getTitle() {
      return title;
   }
   
   public void setTitle(String title) {
      this.title = title;
   }
   
   public int getYear() {
      return year;
   }
   
   public void setYear(int year) {
      this.year = year;
   }
	
	//------------------------------------------------------------
   // Converters
   //
	
	public String toString() {
		return String.format("\"%s\", %d, \"%s\", \"%s\"",
		                     sponsor, year, title, filename);
	}
	
	//------------------------------------------------------------
   // Main
   //
	
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

