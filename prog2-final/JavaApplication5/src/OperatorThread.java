


import java.net.*;
import java.io.*;


public class OperatorThread extends Thread
{  private operaterServer       server    = null;
   public  Socket           socket    = null;
   private int              ID        = -1;
   //private DataInputStream  streamIn  =  null;
   //private DataOutputStream streamOut = null;
   
 private ObjectInputStream  streamIn = null;
 private ObjectOutputStream streamOut = null;
   
   private Thread thread;
   
   public OperatorThread(operaterServer _server, Socket _socket)
   {
	  super();
      server = _server;
      socket = _socket;
      ID     = socket.getPort();
      //streamIn = in;
   }
   
   // send data to client.
   public void send(PassClass pc)    //(PassClass pc)
   {
	   try{
		  //streamOut.writeUTF(msg);
		   streamOut.writeObject(pc);	 
           streamOut.flush();
       }
       catch(IOException ioe)
       {
		  System.out.println(ID + " ERROR sending: " + ioe.getMessage());
          server.remove(ID);
          thread=null;
       }
   }
   public int getID(){
	   return ID;
   }

   // server listening the certain client, receive query and response
   public void run()
   {
	  System.out.println("Server Thread " + ID + " running.");
	  thread = new Thread(this);
	  
	  try {
		   PassClass pc = server.getRouteTable();
		   server.broadcast(getID(), pc);
	   } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
	   
	  try {
		  streamIn = new ObjectInputStream (socket.getInputStream());
	  } catch (IOException e1) {
		  // TODO Auto-generated catch block
		  e1.printStackTrace();
	  }
	  
      while (true){
		 try{
			 //server get message from client, then response  *********streamIn.readUTF()
			 PassClass pc = (PassClass)streamIn.readObject();
			 
//			 for (int i = 0;i<pc.al_sp.size();i++)
//		       {
//		    	   SearchParam sp = pc.al_sp.get(i);
//		    	   System.out.print(sp.toString());
//		       }
			 System.out.print("-----     server receive");
                         if (pc.content.equals("search"))
                         {
                             PassClass searchResult = server.searchTicket(pc);
                             server.broadcast(ID, searchResult);
                         }
                         else if(pc.content.equals("book"))
                         {
                             System.out.print(pc.toString());
                             System.out.print("ticket: "+ pc.bookTicketNo);
                             PassClass bookResult = server.bookTicket(pc);
                             server.broadcast(ID, bookResult);
                         }
                         else if (pc.content.equals("cancel"))
                         {
                              System.out.print(pc.toString());
                              server.cancelTicket(pc);
                         }
                         else {
                              System.out.print("else");
                         }
		 }
		
         catch(IOException ioe){
			//System.out.println(ID + " ERROR reading: " + ioe.getMessage());
            server.remove(ID);
            thread = null;
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
      }
   }

   public void open() throws IOException
   {
//	  streamIn = new DataInputStream(new
//                        BufferedInputStream(socket.getInputStream()));
//      streamOut = new DataOutputStream(new
//	                           BufferedOutputStream(socket.getOutputStream()));
	  
	   System.out.println("open start");
	   streamOut = new ObjectOutputStream(socket.getOutputStream());
	   //streamIn = new ObjectInputStream (socket.getInputStream());
//	   try {
//		   server.getRouteTable();
//		   server.broadcast(getID(), server.pc);
//	   } catch (Exception e) {
//		   // TODO Auto-generated catch block
//		   e.printStackTrace();
//	   }
	   
	   
	   
	   System.out.println("open finish");
	   
   }

   public void close() throws IOException
   {
	   if (socket != null)
	   	socket.close();

      if (streamIn != null)
      	streamIn.close();

      if (streamOut != null)
      	streamOut.close();
   }
}