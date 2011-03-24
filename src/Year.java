/*
 * Year.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */


public class Year {
	
	private static final Integer[] years;
	
	static {
		
		int first = 2010;
		int last = 2025;
		int len = last - first + 1;
		int index = -1;
		
		// Add years
		years = new Integer[len];
		for (int y=first; y<=last; ++y) {
			years[++index] = y;
		}
	}
	
	public static Integer[] getAllYears() {
		return years;
	}
	
	//------------------------------------------------------------
   // Main
   //
	
	public static void main(String[] args) {
		for (int year : Year.getAllYears()) {
			System.out.println(year);
		}
	}
}

