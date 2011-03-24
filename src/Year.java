/*
 * Year.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */



/**
 * 
 */
public class Year {
	
	private static final Integer[] years;
	
	
	static {
		
		int first=2010, last=2025;
		int index=-1;
		
		// Add years
		years = new Integer[last-first+1];
		for (int y=first; y<=last; ++y) {
			years[++index] = y;
		}
	}
	
	
	public static Integer[] getAllYears() {
		
		return years;
	}
	
	
	/**
	 * Test for Year.
	 */
	public static void main(String[] args) {
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Year");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		for (int year : Year.getAllYears()) {
			System.out.println(year);
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Year");
		System.out.println("****************************************");
		System.out.println();
	}
}

