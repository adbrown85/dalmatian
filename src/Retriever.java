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
	
	private static final PreparedStatement filesForBreak;
	private static final PreparedStatement titlesForSponsor;
	private static final PreparedStatement sponsorsWithSpots;
	private static final PreparedStatement yearsForSponsorTitle;
	private static final PreparedStatement spotForSponsorTitleYear;
	private static final PreparedStatement spotIdForSponsorTitleYear;
	
	/** 
	 * Initializes static fields.
	 */
	static {
	   try {
   	   filesForBreak = makeFilesForBreak();
   		titlesForSponsor = makeTitlesForSponsor();
   		sponsorsWithSpots = makeSponsorsWithSpots();
   		yearsForSponsorTitle = makeYearsForSponsorTitle();
   		spotForSponsorTitleYear = makeSpotForSponsorTitleYear();
   		spotIdForSponsorTitleYear = makeSpotIdForSponsorTitleYear();
	   } catch (SQLException e) {
	      throw new ExceptionInInitializerError("Could not make statements!");
	   }
	}
	
	public Vector<String> getFilesForBreak(Break b) throws SQLException {
		
		ResultSet rs;
		Vector<String> files = new Vector<String>();
		
		// Execute query
		filesForBreak.setInt(1, b.getId());
		rs = filesForBreak.executeQuery();
		
		// Process results
		while (rs.next()) {
			files.add(rs.getString(1));
		}
		return files;
	}
	
	public Vector<String> getTitlesForSponsor(String name) throws SQLException {
		
		ResultSet results;
		Vector<String> titles;
		
		// Execute query
		titlesForSponsor.setString(1, name);
		results = titlesForSponsor.executeQuery();
		
		// Process results
		titles = new Vector<String>();
		while (results.next()) {
			titles.add(results.getString(1));
		}
		return titles;
	}
	
	public Vector<String> getSponsorsWithSpots() throws SQLException {
		
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
		
		ResultSet rs;
		Vector<Integer> years = new Vector<Integer>();
		
		// Execute query
		yearsForSponsorTitle.setString(1, sponsor);
		yearsForSponsorTitle.setString(2, title);
		rs = yearsForSponsorTitle.executeQuery();
		
		// Process results
		while (rs.next()) {
			years.add(rs.getInt(1));
		}
		return years;
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
	private static PreparedStatement makeFilesForBreak() throws SQLException {
		return Database.prepare(
		      "SELECT sp.filename " + 
            "FROM slot AS sl JOIN spot AS sp " + 
            "ON sl.spot=sp.id " +
            "WHERE sl.break=?");
	}
	
	private static PreparedStatement makeSponsorsWithSpots()
	                                                       throws SQLException {
		return Database.prepare(
		      "SELECT DISTINCT sponsor " +
		      "FROM spot " +
		      "ORDER BY sponsor");
	}
	
	private static PreparedStatement makeSpotForSponsorTitleYear()
	                                                       throws SQLException {
		return Database.prepare(
		      "SELECT * " +
		      "FROM spot " +
            "WHERE sponsor=? AND title=? AND year=?");
	}
	
	private static PreparedStatement makeSpotIdForSponsorTitleYear()
	                                                       throws SQLException {
		return Database.prepare(
		      "SELECT id " +
		      "FROM spot " +
            "WHERE sponsor=? AND title=? AND year=?");
	}
	
	private static PreparedStatement makeTitlesForSponsor()
	                                                       throws SQLException {
		return Database.prepare(
		      "SELECT DISTINCT title " +
		      "FROM spot " + 
            "WHERE sponsor=? " + 
            "ORDER BY title");
	}
	
	private static PreparedStatement makeYearsForSponsorTitle()
	                                                       throws SQLException {
		return Database.prepare(
		      "SELECT year " +
		      "FROM spot " +
            "WHERE sponsor=? AND title=? " + 
            "ORDER BY year");
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	/**
	 * Test for Retriever.
	 */
	public static void main(String[] args) throws SQLException {
		
		int id, year;
		Retriever retriever = new Retriever();
		Spot spot;
		Vector<String> strings;
		Vector<Integer> integers;
		
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
		year = 2010;
		spot = retriever.getSpotForSponsorTitleYear(sponsor, title, year);
		spot.print();
		
		// Spot id for sponsor, title, year
		System.out.println("\nSpot Id for sponsor, title, year:");
		sponsor = "Tire Land USA";
		title = "Christmas Buy Three Get Fourth Free";
		year = 2010;
		id = retriever.getSpotIdFor(sponsor, title, year);
		System.out.println("  " + id);
		
		// Files for break
		System.out.println("\nFiles for break:");
		strings = retriever.getFilesForBreak(new Break(12));
		for (String string : strings) {
			System.out.println("  " + string);
		}
	}
}

