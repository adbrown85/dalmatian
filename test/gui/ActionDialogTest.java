package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.junit.Test;


public class ActionDialogTest {
   
   @Test
   public void testFireActionEvent() throws Exception {
      
      final JFrame frame = new JFrame("Frame");
      final JButton button = new JButton("Action");
      final ActionDialog ad = new ActionDialog(frame, "Button Dialog");
      
      // Create frame
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setPreferredSize(new Dimension(400, 300));
      frame.pack();
      
      // Create dialog with button
      button.setActionCommand("Action");
      ad.add(button);
      button.addActionListener(ad);
      ad.pack();
      ad.setLocationRelativeTo(frame);
      ad.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            System.out.println(event.getActionCommand());
         }
      });
      ad.fireActionEvent("Action fired from ActionDialog.");
      
      // Show frame and dialog
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            frame.setVisible(true);
            ad.setVisible(true);
         }
      });
      Thread.sleep(4000);
   }
}
