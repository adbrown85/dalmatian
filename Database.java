/*
 * Database.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * Utility class for executing queries against the Dalmatian database.
 */
public class Database {
	
	
	private static Connection connection;
	
	
	
	/**
	 * Initializes the connection to the database.
	 */
	private static void initConnection() {
		
		String host, name, user, password, url;
		
		try {
			
			// Find credentials from XML file
			host = Configuration.getOption("database", "host");
			name = Configuration.getOption("database", "name");
			user = Configuration.getOption("database", "user");
			password = Configuration.getOption("database", "password");
			
			// Make connection
			url = String.format("jdbc:mysql://%s/%s", host, name);
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.println("[Database] Could not find MySQL driver.");
			System.exit(1);
		} catch (SQLException e) {
			System.err.println("[Database] No connection to database.");
			System.exit(1);
		}
	}
	
	
	
	/**
	 * Executes a query on the database.
	 */
	public static ResultSet executeQuery(String query) 
	                                     throws SQLException {
		
		Statement statement;
		
		// Check connection
		if (connection == null)
			initConnection();
		
		// Pass statement
		statement = connection.createStatement();
		return statement.executeQuery(query);
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
			rs = Database.executeQuery("SELECT * FROM address");
			while (rs.next()) {
				System.out.println(rs.getString("street"));
				System.out.println(rs.getString("city"));
				System.out.println(rs.getString("state"));
				System.out.println(rs.getString("zip"));
				System.out.println();
			}
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

