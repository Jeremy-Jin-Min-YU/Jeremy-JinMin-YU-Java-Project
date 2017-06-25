


import java.net.*;
import java.io.*;
import java.util.ArrayList;



public class AgentThread extends Thread
{  private Socket           socket   = null;
   private Agent       client   = null;
   //private DataInputStream  streamIn = null;
   private ObjectInputStream in;
   private ObjectOutputStream out;
   private Thread thread;
   private int              ID        = -1;
   public boolean bookReturn ;

   public AgentThread(Agent _client, Socket _socket)
   {  
      client   = _client;
      socket   = _socket;
      ID     = socket.getPort();
      //open();
      //start();
   }
   public void open()
   {  
        try
      {
		  //streamIn  = new DataInputStream(socket.getInputStream());
          in = new ObjectInputStream(socket.getInputStream());
          out = new ObjectOutputStream(socket.getOutputStream());
      }
      catch(IOException ioe)
      {
		 System.out.println("Error getting input stream: 11111" + ioe);
         client.stop();
      }
   }
   public void close()
   {  try
      {  
	   		if (in != null) in.close();
      }
      catch(IOException ioe)
      {  
    	  System.out.println("Error closing input stream: " + ioe);
      }
   }

   public int getID(){
	   return ID;
   }

  

   public void send(String operator,PassClass pc)    //(PassClass pc)
   {
    try{
            out.writeObject(pc);
            out.flush();
       }
       catch(IOException ioe)
       {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            
            client.remove(operator);
          thread=null;
       }
   }
  

    // thread listening to the server
   public void run()
   {
       System.out.println("Server Thread " + ID + " running.");
	  thread = new Thread(this);
	   while (true && client!= null){
		   
		  try {
		      		       
			  PassClass pc = (PassClass)(in.readObject());

//                           if (pc.content.equals("bookResult"))
//                            {
//                                if (pc.bookResult > 0)
//                                {
//                                    bookReturn =
//                                }
//
//                            }
//                            else
//                            {
                                   client.handle(ID,pc);
//                            }
			  
          }
		  catch(IOException ioe)
		  {
			  System.out.println("111111111111111");
			  client = null;
			  System.out.println("Listening error: " + ioe.getMessage());
			  ioe.printStackTrace();
		  }
		  catch (Exception e)
		  {
			  System.out.println("222222222222222222222");
			  client = null;
			  System.out.println("Listening error: " + e.getMessage());
			  e.printStackTrace();
		  }
      }
   }
}



