


import java.awt.event.*;

public class ExitListener extends WindowAdapter {

   Agent client;

   public ExitListener(Agent client) {
      this.client = client;
   }
      
   public void windowClosing (WindowEvent e) {
      client.stop();
      System.exit(0);
   }
}
