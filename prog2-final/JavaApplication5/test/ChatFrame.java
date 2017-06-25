


import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatFrame extends Frame { 

   protected TextArea output;				//show message area
   protected TextField input;				// user input message area 
   protected JPanel tpanel;
   protected JLabel time_input;				// show time area
   
   protected Thread listener;
  
   public ChatFrame (String title){
      super (title); 
    
      setLayout (new BorderLayout ()); 
      
      add ("Center", output = new TextArea ()); 
      output.setEditable (false); 
      add ("South", input = new TextField ()); 
      
     try{
    	 
      tpanel = new JPanel();
      tpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      add ("North", tpanel);
      
      time_input = new JLabel();
      time_input.setText("2:00");
      Font fancyFont = new Font("Serif",Font.BOLD | Font.ITALIC,32); 
//    	   Associate the font with the label
      time_input.setFont(fancyFont);
//    set color
      time_input.setForeground(Color.RED);
      tpanel.add(time_input);
      pack (); 
      show (); 
      input.requestFocus (); 
     }
      catch (Exception e)
      {
          e.printStackTrace();
      }
   }
  
   public static void main (String args[]) { 
      new ChatFrame("Chat "); 
   }
}

