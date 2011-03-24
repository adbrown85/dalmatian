/*
 * Formatter.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package util;


/**
 * Formats strings.
 */
public class Formatter {
	
	/**
	 * Formats a string for a SQL statement;
	 */
	public static String formatSQL(String string) {
		if (string != null) {
			return String.format("'%s'", string);
		} else {
			return "NULL";
		}
	}
	
	/**
	 * Converts a string to title case.
	 */
	public static String toTitleCase(String string) {
		
		char[] chars = string.toCharArray();
		
		// Capitalize first and characters after spaces
		chars[0] = Character.toUpperCase(chars[0]);
		for (int i=1; i<chars.length-1; ++i) {
			if (chars[i] == ' ') {
				++i;
				chars[i] = Character.toUpperCase(chars[i]);
			}
		}
		return new String(chars);
	}
	
	/**
	 * Test for SQL.
	 */
	public static void main(String[] args) {
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Formatter");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		System.out.println(Formatter.formatSQL(null));
		System.out.println(Formatter.formatSQL("405 Lincoln St"));
		System.out.println(Formatter.toTitleCase("the blah blah"));
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Formatter");
		System.out.println("****************************************");
		System.out.println();
	}
}

