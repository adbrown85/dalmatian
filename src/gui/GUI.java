/*
 * GUI.java
 * 
 * Author
 *     Andrew Brown <andrew@andrewdbrown.com>
 */
package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * GUI utilities.
 */
public class GUI {
	
	public static Dimension getInfiniteSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	public static JComboBox getComboBoxFor(int start, int end) {
		
		JComboBox cb = new JComboBox();
		
		for (int i=start; i<=end; ++i) {
			cb.addItem(i);
		}
		return cb;
	}
	
	public static JScrollPane getScrollPaneFor(Component component) {
		return getScrollPaneFor(component, 0);
	}
	
	public static JScrollPane getScrollPaneFor(Component component,
	                                           int borderWidth) {
		
		int policy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		JScrollPane sp = new JScrollPane(component);
		
		sp.setMaximumSize(getInfiniteSize());
		sp.setVerticalScrollBarPolicy(policy);
		if (borderWidth > 0) {
			setScrollPaneBorder(sp, borderWidth);
		}
		return sp;
	}
	
	public static void setScrollPaneBorder(JScrollPane scrollPane,
	                                       int width) {
		
		Border border = createEmptyBorder(width);
		
		setScrollPaneBorder(scrollPane, border);
	}
	
	public static void setScrollPaneBorder(JScrollPane scrollPane,
	                                       Border border) {
		
		Border inside = scrollPane.getBorder();
		Border compound = createCompoundBorder(border, inside);
		
		scrollPane.setBorder(compound);
	}
	
	public static boolean showConfirmDelete(Component parent) {
		
		int style = JOptionPane.YES_NO_OPTION;
		String message = "Are you sure you want to delete this item?";
		String title = "Confirm Delete";
		int option = showConfirmDialog(parent, message, title, style);
		
		return option == JOptionPane.YES_OPTION;
	}
	
   private static int showConfirmDialog(Component parent,
                                        String message,
                                        String title,
                                        int style) {
      return JOptionPane.showConfirmDialog(parent, message, title, style);
   }
   
	public static void showError(Component parent, String message) {
		
		int type = JOptionPane.ERROR_MESSAGE;
		String title = "Error";
		
		JOptionPane.showMessageDialog(parent, message, title, type);
	}
	
	public static void showMessage(Component parent,
	                               String message) {
		JOptionPane.showMessageDialog(parent, message);
	}
	
	//------------------------------------------------------------
   // Helpers
   //
	
   private static Border createEmptyBorder(int width) {
      return BorderFactory.createEmptyBorder(width, width, width, width);
   }
   
   private static Border createCompoundBorder(Border ob, Border ib) {
      return BorderFactory.createCompoundBorder(ob, ib);
   }
   
   //------------------------------------------------------------
   // Main
   //
   
   public static void main(String[] args) {
      
      JFrame frame;
      
      // Start
      System.out.println();
      System.out.println("****************************************");
      System.out.println("GUI");
      System.out.println("****************************************");
      System.out.println();
      
      // Create frame
      frame = new JFrame("Frame");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setPreferredSize(new Dimension(320, 240));
      frame.pack();
      frame.setVisible(true);
      
      // Show error
      GUI.showError(frame, "This is an error!");
      
      // Finish
      System.out.println();
      System.out.println("****************************************");
      System.out.println("GUI");
      System.out.println("****************************************");
      System.out.println();
   }
}

