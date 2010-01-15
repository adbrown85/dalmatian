/*
 * Configuration.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * Utility for reading Dalmatian's XML configuration file.
 */
public class Configuration {
	
	
	private static final String filename="configuration.xml";
	private static Document document=null;
	
	
	
	/**
	 * Gets the value of an option.
	 * 
	 * @param category
	 *     Category the option is located in.
	 * @param option
	 *     Option to retrieve.
	 */
	public static String getOption(String category,
	                               String option)
	                               throws IOException {
		
		NodeList nodeList;
		Element categoryElement, optionElement;
		
		// Check if document hasn't been parsed
		if (document == null)
			initDocument();
		
		// Get the section
		nodeList = document.getElementsByTagName(category);
		if (nodeList.getLength() == 0)
			throw new IOException("[Configuration] Could not find category.");
		categoryElement = (Element)nodeList.item(0);
		
		// Get the value
		nodeList = categoryElement.getElementsByTagName(option);
		if (nodeList.getLength() == 0)
			throw new IOException("[Configuration] Could not find option.");
		optionElement = (Element)nodeList.item(0);
		return optionElement.getFirstChild().getNodeValue();
	}
	
	
	
	/**
	 * Initializes and parses the document.
	 */
	public static void initDocument() {
		
		DocumentBuilder builder;
		DocumentBuilderFactory factory;
		NodeList list;
		
		try {
			
			// Parse the document
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(filename);
		} catch (ParserConfigurationException pce) {
			System.err.println("[DatabaseHelper] Cannot create XML parser.");
			System.exit(1);
		} catch (SAXException se) {
			System.err.println("[DatabaseHelper] Cannot parse config file.");
			System.exit(1);
		} catch (IOException ie) {
			System.err.println("[DatabaseHelper] Cannot open config file.");
			System.exit(1);
		}
	}
	
	
	
	/**
	 * Tests %Configuration.
	 */
	public static void main(String[] args) {
		
		String host, name, user, password;
		
		// Start
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Configuration");
		System.out.println("****************************************");
		System.out.println();
		
		// Test
		try {
			host = Configuration.getOption("database", "host");
			name = Configuration.getOption("database", "name");
			user = Configuration.getOption("database", "user");
			password = Configuration.getOption("database", "password");
			System.out.printf("%s %s %s %s\n", host, name, user, password);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		// Finish
		System.out.println();
		System.out.println("****************************************");
		System.out.println("Configuration");
		System.out.println("****************************************");
		System.out.println();
	}
}

