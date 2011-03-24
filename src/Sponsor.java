/*
 * Sponsor.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Vector;


/**
 * Company or organization that pays for a spot.
 */
public class Sponsor {
	
	private static final PreparedStatement deleteStatement;
	private static final PreparedStatement insertStatement;
	private static final PreparedStatement selectStatement;
	private static final PreparedStatement updateStatement;
	
	private String name;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
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
			throw new ExceptionInInitializerError("Could not makes statements!");
		}
	}
	
	/**
	 * Creates a new, empty Sponsor.
	 */
	public Sponsor() {
		name = null;
		street = null;
		city = null;
		state = null;
		zip = null;
		phone = null;
	}
	
	/**
	 * Creates a new Sponsor from one already in the database.
	 */
	public Sponsor(String name) throws SQLException {
		
		ResultSet rs;
		
		// Execute query
		selectStatement.setString(1, name);
		rs = selectStatement.executeQuery();
		
		// Process results
		rs.next();
		this.name = rs.getString("name");
		this.street = rs.getString("street");
		this.city = rs.getString("city");
		this.state = rs.getString("state");
		this.zip = rs.getString("zip");
		this.phone = rs.getString("phone");
	}
	
	/**
	 * Creates a new Sponsor by copying another.
	 */
	public Sponsor(Sponsor other) {
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
	public static void delete(String name) throws SQLException {
		
		int result;
		
		// Execute update
		deleteStatement.setString(1, name);
		result = deleteStatement.executeUpdate();
		
		// Check results
		if (result != 1) {
			throw new SQLException("[Sponsor] No row was deleted.");
		}
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
	
	public static Vector<String> getAllNames() throws SQLException {
		
		String sql = "SELECT name FROM sponsor ORDER BY name";
		Vector<String> names = new Vector<String>();
		ResultSet rs = Database.executeQuery(sql);
		
		while (rs.next()) {
			names.add(rs.getString(1));
		}
		return names;
	}
	
   /**
    * Inserts the sponsor into the database.
    */
   public void insert() throws SQLException {
      
      int result;
      
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
   
   public static void update(Sponsor original,
                             Sponsor updated)
                             throws SQLException {
      updateStatement.setString(1, updated.getName());
      updateStatement.setString(2, updated.getStreet());
      updateStatement.setString(3, updated.getCity());
      updateStatement.setString(4, updated.getState());
      updateStatement.setString(5, updated.getZip());
      updateStatement.setString(6, updated.getPhone());
      updateStatement.setString(7, original.getName());
      updateStatement.executeUpdate();
   }
	
	//------------------------------------------------------------
   // Helpers
   //
	
	private static PreparedStatement makeDeleteStatement() throws SQLException {
		return Database.prepare(
		      "DELETE " +
		      "FROM sponsor " +
		      "WHERE name = ? LIMIT 1");
	}
	
	private static PreparedStatement makeInsertStatement() throws SQLException {
		return Database.prepare(
		      "INSERT INTO sponsor(name, street, city, state, zip, phone) " +
            "VALUES(?, ?, ?, ?, ?, ?)");
	}
	
	private static PreparedStatement makeSelectStatement() throws SQLException {
		return Database.prepare(
		      "SELECT * " +
		      "FROM sponsor " +
		      "WHERE name = ?");
	}
	
	private static PreparedStatement makeUpdateStatement() throws SQLException {
		return Database.prepare(
		      "UPDATE sponsor " + 
            "SET name=?, street=?, city=?, state=?, zip=?, phone=?" + 
            "WHERE name=?");
	}
	
	//------------------------------------------------------------
   // Getters and setters
   //
	
   public String getName() {
      return name;
   }
   
   public String getStreet() {
      return street;
   }
   
   public String getCity() {
      return city;
   }
   
   public String getState() {
      return state;
   }
   
   public String getZip() {
      return zip;
   }
   
   public String getPhone() {
      return phone;
   }
   
	public void setName(String name) {
	   this.name = name;
	}
	
	public void setStreet(String street) {
	   this.street = street;
	}
	
	public void setCity(String city) {
	   this.city = city;
	}
	
	public void setState(String state) {
	   this.state = state;
	}
	
	public void setZip(String zip) {
	   this.zip = zip;
	}
	
	public void setPhone(String phone) {
	   this.phone = phone;
	}
	
	//------------------------------------------------------------
   // Converters
   //
	
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s",
		                     name, street, city, state, zip, phone);
	}
	
	//------------------------------------------------------------
   // Main
   //
	
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

