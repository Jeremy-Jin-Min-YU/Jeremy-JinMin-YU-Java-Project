


import java.awt.event.*;

public class EnterListener extends KeyAdapter {
   
   Agent client;
   ChatFrame gui;

   public EnterListener (Agent client, ChatFrame gui) {
      this.client = client;
      this.gui = gui;
   }   

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode()==KeyEvent.VK_ENTER) {
    	  // user input then send to server   
         // client.sendTextToChat(gui.input.getText());
          gui.input.setText("");
      }
   }
}
