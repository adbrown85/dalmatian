/*
 * Retriever.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.sql.*;
import java.util.Vector;



/**
 * Retrieve various things from the database.
 */
public class Retriever {
	
	private static PreparedStatement titlesForSponsor=null;
	private static PreparedStatement sponsorsWithSpots=null;
	private static PreparedStatement yearsForSponsorTitle=null;
	private static PreparedStatement spotForSponsorTitleYear=null;
	private static PreparedStatement spotIdForSponsorTitleYear=null;
	
	
	public Retriever()
	                 throws SQLException {
		
		// Initialize
		initTitlesForSponsor();
		initSponsorsWithSpots();
		initSpotForSponsorTitleYear();
		initSpotIdForSponsorTitleYear();
		initYearsForSponsorTitle();
	}
	
	
	public Vector<String> getTitlesForSponsor(String sponsorName)
	                                          throws SQLException {
		
		ResultSet results;
		Vector<String> titles;
		
		// Execute query
		titlesForSponsor.setString(1, sponsorName);
		results = titlesForSponsor.executeQuery();
		
		// Process results
		titles = new Vector<String>();
		while (results.next()) {
			titles.add(results.getString(1));
		}
		return titles;
	}
	
	
	public Vector<String> getSponsorsWithSpots()
	                                           throws SQLException {
		
		ResultSet results;
		Vector<String> sponsors;
		
		// Execute query and process results
		results = sponsorsWithSpots.executeQuery();
		sponsors = new Vector<String>();
		while (results.next()) {
			sponsors.add(results.getString(1));
		}
		return sponsors;
	}
	
	
	public Spot getSpotForSponsorTitleYear(String sponsor,
	                                       String title,
	                                       Integer year)
	                                       throws SQLException {
		
		ResultSet results;
		Vector<String> sponsors;
		
		// Execute query and process results
		spotForSponsorTitleYear.setString(1, sponsor);
		spotForSponsorTitleYear.setString(2, title);
		spotForSponsorTitleYear.setInt(3, year);
		results = spotForSponsorTitleYear.executeQuery();
		results.next();
		return new Spot(results);
	}
	
	
	public int getSpotIdFor(String sponsor,
	                        String title,
	                        Integer year)
	                        throws SQLException {
		
		ResultSet results;
		Vector<String> sponsors;
		
		// Execute query and process results
		spotIdForSponsorTitleYear.setString(1, sponsor);
		spotIdForSponsorTitleYear.setString(2, title);
		spotIdForSponsorTitleYear.setInt(3, year);
		results = spotIdForSponsorTitleYear.executeQuery();
		results.next();
		return (results.getInt(1));
	}
	
	
	public Vector<Integer> getYearsForSponsorTitle(String sponsor,
	                                               String title)
	                                               throws SQLException {
		
		ResultSet results;
		Vector<Integer> years;
		
		// Execute query
		yearsForSponsorTitle.setString(1, sponsor);
		yearsForSponsorTitle.setString(2, title);
		results = yearsForSponsorTitle.executeQuery();
		
		// Process results
		years = new Vector<Integer>();
		while (results.next()) {
			years.add(results.getInt(1));
		}
		return years;
	}
	
	
	private void initSponsorsWithSpots()
	                                   throws SQLException {
		
		String sql;
		
		if (sponsorsWithSpots != null)
			return;
		
		sql = "SELECT DISTINCT sponsor FROM spot ORDER BY sponsor";
		sponsorsWithSpots = Database.prepareStatement(sql);
	}
	
	
	private void initSpotForSponsorTitleYear()
	                                         throws SQLException {
		
		String sql;
		
		if (spotForSponsorTitleYear != null)
			return;
		
		sql = "SELECT * FROM spot " +
		      "WHERE sponsor=? AND title=? AND year=?";
		spotForSponsorTitleYear = Database.prepareStatement(sql);
	}
	
	
	private void initSpotIdForSponsorTitleYear()
	                                           throws SQLException {
		
		String sql;
		
		if (spotIdForSponsorTitleYear != null)
			return;
		
		sql = "SELECT id FROM spot " +
		      "WHERE sponsor=? AND title=? AND year=?";
		spotIdForSponsorTitleYear = Database.prepareStatement(sql);
	}
	
	
	private void initTitlesForSponsor()
	                                  throws SQLException {
		
		String sql;
		
		if (sponsorsWithSpots != null)
			return;
		
		sql = "SELECT DISTINCT title FROM spot " + 
		      "WHERE sponsor=? " + 
		      "ORDER BY title";
		titlesForSponsor = Database.prepareStatement(sql);
	}
	
	
	private void initYearsForSponsorTitle()
	                                      throws SQLException {
		
		String sql;
		
		if (yearsForSponsorTitle != null)
			return;
		
		sql = "SELECT year FROM spot " +
		      "WHERE sponsor=? AND title=? " + 
		      "ORDER BY year";
		yearsForSponsorTitle = Database.prepareStatement(sql);
	}
	
	
	/**
	 * Test for Retriever.
	 */
	public static void main(String[] args) {
		
		int id, year;
		Retriever retriever;
		Spot spot;
		Vector<String> strings;
		Vector<Integer> integers;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Retriever");
		System.out.println("****************************************");
		System.out.println();
		
		try {
			
			retriever = new Retriever();
			
			// Titles for sponsor
			System.out.println("Titles for sponsor 'Tire Land USA':");
			strings = retriever.getTitlesForSponsor("Tire Land USA");
			for (String string : strings) {
				System.out.println("  " + string);
			}
			
			// Sponsors with spots
			System.out.println("\nSponsors with spots:");
			strings = retriever.getSponsorsWithSpots();
			for (String string : strings) {
				System.out.println("  " + string);
			}
			
			// Years for sponsor and title
			System.out.println("\nYears for sponsor and title:");
			String sponsor = "Tire Land USA";
			String title = "Christmas Buy Three Get Fourth Free";
			integers = retriever.getYearsForSponsorTitle(sponsor, title);
			for (Integer integer : integers) {
				System.out.println("  " + integer);
			}
			
			// Spot for sponsor, title, year
			System.out.println("\nSpot for sponsor, title, year:");
			sponsor = "Tire Land USA";
			title = "Christmas Buy Three Get Fourth Free";
			year = 2009;
			spot = retriever.getSpotForSponsorTitleYear(sponsor, title, year);
			spot.print();
			
			// Spot id for sponsor, title, year
			System.out.println("\nSpot Id for sponsor, title, year:");
			sponsor = "Tire Land USA";
			title = "Christmas Buy Three Get Fourth Free";
			year = 2009;
			id = retriever.getSpotIdFor(sponsor, title, year);
			System.out.println("  " + id);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Retriever");
		System.out.println("****************************************");
		System.out.println();
	}
}

