


import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.io.*;

public class operaterServer implements Runnable
{  
					
   // Array of clients threads	
   private OperatorThread clients[] = new OperatorThread[50];
   private ServerSocket server = null;
   private Thread       thread = null;
   private int clientCount = 0;
   
   //private DataOutputStream  dos = null;
   private Socket s;
   //public PassClass pc= null;
   
   //private ObjectOutputStream out;
   //private ObjectInputStream in;
   
   private  Connection conn = null; 
   
   public void connect() throws Exception
   {
		   String userName = "root";
		   String password = "admin";
		   String url = "jdbc:mysql://localhost/test";
		   Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		   conn = DriverManager.getConnection (url, userName, password);
		   System.out.println ("Database connection established"); 
		   Thread.sleep(3000);
   }
   
   
   public PassClass getRouteTable() throws Exception
   {
	   PassClass pc = null;
	   Statement s = conn.createStatement ();
       s.execute ("CALL operator1.getRouteCity()");
       ResultSet rs = s.getResultSet ();
       int count = 0;
       ArrayList <Route> set = new  ArrayList <Route>();
       while (rs.next ())
       {
    	   String dep = rs.getString (1);
    	   String arr = rs.getString (2);
    	  
    	   System.out.println (
    			   dep + ", " + arr 
    	   );

    	   set.add(new Route(dep,arr));
    	   ++count;
       }
       rs.close ();
       s.close ();
       
       System.out.println (count + " rows were retrieved");

       pc = new PassClass ("route","operator1");  // pack up for send
       pc.al_route = set;
       return pc;
   }

   public synchronized PassClass bookTicket(PassClass pc)
   {
       PassClass bookResult = new PassClass("bookResult","operator1");

        Boolean hadResult = false;
	int book_id = 0;
       try {
               CallableStatement book = conn.prepareCall("{CALL operator1.insert_book(?)}");

               book.setInt(1, pc.bookTicketNo);

               hadResult = book.execute();

               if (hadResult)
               {
                    ResultSet rs = book.getResultSet();
                     while (rs.next ())
                    {
                        book_id = rs.getInt(1);         // > 0 book success ,   = 0 book is full, can not book more
                        bookResult.bookId = book_id;
                    }
               }
               else
               {
                   bookResult.bookId = -1;
               }
           } catch (Exception e) {
                   bookResult.bookId = -1;
                   // TODO Auto-generated catch block
                   e.printStackTrace();
           }
       //System.out.print(sp.toString());
         System.out.println("bookResult" + bookResult.bookId);
         return bookResult;
   }

   // book ticket
   public synchronized void cancelTicket(PassClass pc)
   {
         try {
             int id = pc.bookId;
               CallableStatement cancel = conn.prepareCall("{CALL operator1.delete_book(?)}");

               cancel.setInt(1, pc.bookId);

               boolean hadResult = cancel.execute();
         }
         catch (Exception e)
         {
              e.printStackTrace();
         }
   }


   //search tickets
   public  PassClass searchTicket(PassClass pc)
   {
	   PassClass searchResult = null;
	   System.out.println("server search tickets..............");
	   ArrayList <SearchResult> set = new  ArrayList <SearchResult>();
	   for (int i = 0;i<pc.al_sp.size();i++)
	   {
		   Boolean hadResult = false;
		   SearchParam sp = pc.al_sp.get(i);
		   try {
			   CallableStatement getTicket = conn.prepareCall("{call operator1.search_ticket(?,?,?)}");
			
			   getTicket.setString(1, sp.dep);
			   getTicket.setString(2, sp.arr);
			   getTicket.setString(3, sp.DateStr);
			   
			   hadResult = getTicket.execute();
			   
			   if (hadResult)
			   {
				   ResultSet rs = getTicket.getResultSet();
				   			   
				   while (rs.next ())
				   {
					   String dep = rs.getString (1);
					   String arr = rs.getString (2);
					   String dep_time = rs.getString (3);
					   String arr_time =rs.getString (4);
					   double price = rs.getDouble (5);
					   int avai =rs.getInt (6);
					   String operator =rs.getString (7);
                                           int ticket = rs.getInt(8);
					   SearchResult sr = new SearchResult(dep,arr,dep_time,arr_time,price,avai,operator,ticket);
					   set.add(sr);
					   System.out.print(sr.toString());
				   }
			   }
		   } catch (Exception e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   //System.out.print(sp.toString());
		   
	   }
	   searchResult = new PassClass ("SearchResult","operator1");  // pack up for send
	   searchResult.al_sr = set;
       return searchResult;
   }
   
   public operaterServer (int port)
   {
	  try {
		  
		 connect();
		 
		 System.out.println("Binding to port " + port + ", please wait  ...");
         server = new ServerSocket(port);
         System.out.println("Server started: " + server.getInetAddress());
         
         start();
      }
      catch(IOException ioe)
      {
		  System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
      }
      catch (Exception e)
      {
    	  System.err.println ("Cannot connect to database");
    	  e.printStackTrace();
    	  
      }
   }

   public void run()
   {
	  while (thread != null)
      {
		 try{
			 
			System.out.println("Waiting for a client ...");
			s = server.accept();
//			out = new ObjectOutputStream(s.getOutputStream());
//			
//			getRouteTable();
//			sendItem();

            addThread(s);					//declare a new client and put it in the array
         
         }
         catch(IOException ioe){
			System.out.println("Server accept error: " + ioe);
			stop();
         }
         catch (Exception e)
         {
        	System.out.println("server can not get data from db: " + e);
 			stop();
         }
         
      }
   }
   
  
   
   
  public void start()
    {
		if (thread == null) {
		  thread = new Thread(this);
          thread.start();
       }
    }

   public void stop(){
	   thread = null;

   }

   private int findClient(int ID)
   {
	   for (int i = 0; i < clientCount; i++)
         if (clients[i].getID() == ID)
            return i;
      return -1;
   }

   // receive query from client and reply (ticket search, book) and reply. call from server thread
   public synchronized void broadcast(int ID, PassClass input)
   {
	   
	   //clients[findClient(ID)].send(ID + ": " + input);
	   
	   clients[findClient(ID)].send(input);
         
  	   System.out.println("broadcast");
  	   	
       notifyAll();
   }
   
   // remove client from client thread array, 
   public synchronized void remove(int ID)
   {
	  int pos = findClient(ID);
      if (pos >= 0){
		 OperatorThread toTerminate = clients[pos];
         System.out.println("Removing client thread " + ID);

         if (pos < clientCount-1)
            for (int i = pos+1; i < clientCount; i++)
               clients[i-1] = clients[i];
         clientCount--;

         try{
			 toTerminate.close();
	     }
         catch(IOException ioe)
         {
			 System.out.println("Error closing thread: " + ioe);
		 }
		 toTerminate = null;
		 
		 notifyAll();
      }
   }

   // add new client thread to array.
   private void addThread(Socket socket)
   {
	   System.out.println("in add thread");
	  if (clientCount < clients.length){

		 System.out.println("Client accepted: " + socket);
		 	
         clients[clientCount] = new OperatorThread(this, socket);
         System.out.println("new instance");
         
         try{
			 clients[clientCount].open();
			 
			 clients[clientCount].start();
            
//            System.out.println("start send city table.");
//            getCityTable();
//            clients[clientCount].send(pc);
//            System.out.println("city table sended");
//			//Thread.sleep(3000);
//			getTrainTable();
//			clients[clientCount].send(pc);
//			
//			getRouteTable();
//			clients[clientCount].send(pc);
//			
//			getTicketTable();
//			clients[clientCount].send(pc);
            
            clientCount++;
         }
         catch(IOException ioe){
			 System.out.println("Error opening thread: " + ioe);
		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
         System.out.println("finish add thread");
	  }
      else
         System.out.println("Client refused: maximum " + clients.length + " reached.");
   }


   public static void main(String args[]) {
	   operaterServer server = null;
//      if (args.length != 1)
//         System.out.println("Usage: java operaterServer port");
//      else
         //server = new operaterServer(1234);
           server = new operaterServer(Integer.parseInt(args[0]));
           //server = new operaterServer();
   }

}