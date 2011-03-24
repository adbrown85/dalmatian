package data;
/*
 * Database.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;


/**
 * Utility class for executing queries against the Dalmatian database.
 */
public class Database {
	
	private static final Connection connection;   // Connection to database
	
	/** Initializes static fields. */
	static {
	   try {
	      connection = makeConnection();
	   } catch (Exception e) {
	      throw new ExceptionInInitializerError("Could not make connection!");
	   }
	}
	
	/**
	 * Gets a new statement from the static connection.
	 * 
	 * @throws SQLException if a statement cannot be made.
	 */
	public static Statement createStatement() throws SQLException {
		return connection.createStatement();
	}
	
	/**
	 * Executes a query on the database.
	 * 
	 * @param sql SQL statement
	 * @throws SQLException if query could not be executed
	 */
	public static ResultSet executeQuery(String sql) throws SQLException {
		return createStatement().executeQuery(sql);
	}
	
	/**
	 * Updates, inserts, or deletes something in the database.
	 * 
    * @param sql SQL statement
    * @throws SQLException if update could not be executed
	 */
	public static int executeUpdate(String sql) throws SQLException {
		return createStatement().executeUpdate(sql);
	}
	
	/**
	 * Returns the connection to the database.
	 */
	public static Connection getConnection() {
		return connection;
	}
	
	/**
	 * Returns the current time according to the database.
	 * 
	 * @throws SQLException if problem executing query
	 */
	public static Timestamp getCurrentTimestamp() throws SQLException {
		
		ResultSet rs = Database.executeQuery("SELECT CURRENT_TIMESTAMP");
		
		rs.next();
		return rs.getTimestamp(1);
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
	/**
	 * Initializes the connection to the database.
	 * 
	 * @throws SQLException if there's a problem connecting.
	 */
	private static Connection makeConnection() throws Exception {
		
		String host = Configuration.getOption("database", "host");
		String name = Configuration.getOption("database", "name");
		String user = Configuration.getOption("database", "user");
	   String password = Configuration.getOption("database", "password");
	   String url = String.format("jdbc:mysql://%s/%s", host, name);
		
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(url, user, password);
	}
	
	/** Creates a prepared statement with the connection. */
	public static PreparedStatement prepare(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}
	
	/**
	 * Tests %Database.
	 */
	public static void main(String[] args) {
		
		ResultSet rs;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Database");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		try {
			rs = Database.executeQuery("SELECT * FROM sponsor");
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("street"));
				System.out.printf("%s, %s %s\n",
				                  rs.getString("city"),
				                  rs.getString("state"),
				                  rs.getString("zip"));
				System.out.println(rs.getString("phone"));
				System.out.println();
			}
			System.out.println(Database.getCurrentTimestamp());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Database");
		System.out.println("****************************************");
		System.out.println();
	}
}

