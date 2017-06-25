



import java.net.*;
import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;


public class Agent //implements Runnable
{
   private Socket socket              = null;
   private Thread thread              = null;
   private BufferedReader  console    = null;
   //private DataOutputStream streamOut = null;
   //private ObjectOutputStream streamOut = null;
   
   private AgentThread clientT    = null;
   private String chatName;
   // user interface
   public ChatFrame gui;
   
   public GUI frame;
   //
   //private static int p;
   private  Connection conn = null; 
   private 	Graph g = null;
   //private Object[] objArr = null;
   private ArrayList <Node> nl = null;
   public ArrayList <ArrayList>finalList = null;
   public PassClass pc = null;
   
   // array of Threads to servers
   private AgentThread clients[] = new AgentThread[50];
   public HashMap       hashmap       =       new       HashMap();
   private int clientCount = 0;
   public int returnCount = -1;
   public ArrayList <BookReturn> bookReturnList  = new ArrayList<BookReturn>();
   
   public ArrayList<RouteTimePrice> sortList = new ArrayList<RouteTimePrice>();
   public ArrayList<SearchResult> directly = new ArrayList<SearchResult>();
   public ArrayList<ArrayList<SearchResult>> notDirectly = new ArrayList<ArrayList<SearchResult>>();
   public   ArrayList<SearchResult> step1 = new ArrayList<SearchResult>();
   public   ArrayList<SearchResult> step2 = new ArrayList<SearchResult>();
   public ArrayList<RouteTimePrice> shortTimeList = null;
   public ArrayList<RouteTimePrice> lowPriceList = null;
  
   
   public void connect() throws Exception
   {
       String userName = "root";
       String password = "admin";
       String url = "jdbc:mysql://localhost/test";
       Class.forName ("com.mysql.jdbc.Driver").newInstance ();
       conn = DriverManager.getConnection (url, userName, password);
       System.out.println ("Database connection established");
       //Thread.sleep(3000);
   }
    
   public Agent()
   {
       frame = new GUI(this);
       frame.setVisible(true);

       frame.appendText("\n"+"Establishing connection. Please wait ...");
       //this.chatName = name;

       try{
            connect();
            File file = new File("D:\\prog2\\JavaApplication5\\build\\classes\\agent.txt");
            BufferedReader input = null;
            try {
                input =  new BufferedReader(new FileReader(file));
                String line = null;
                while(( line = input.readLine()) != null)
                {
                        String[] lineStr = line.split(" ");
                        socket = new Socket(lineStr[0],Integer.parseInt(lineStr[1]));
                        addThread(socket);  
                }
                
            }
            catch (FileNotFoundException e) {
                  e.printStackTrace();
                } catch (IOException e) {
                  e.printStackTrace();
                }
            finally  {
                input.close();
                }
                 				//declare a new client and put it in the array
          }
          catch(UnknownHostException uhe){
                      System.out.println("Host unknown: " + uhe.getMessage());
              }
          catch(IOException ioe){
                      System.out.println("Unexpected exception: " + ioe.getMessage());
              }
          catch (Exception e)
          {
              System.err.println ("Cannot connect to database");
          }
      
   }

   private void addThread(Socket socket)
   {
       if (clientCount < clients.length){

		 System.out.println("Client accepted: " + socket);

         clients[clientCount] = new AgentThread(this, socket);
         //clientT = new AgentThread(this, socket);
         System.out.println("new instance");

         try{
            clients[clientCount].open();

            clients[clientCount].start();

            clientCount++;
         }
//         catch(IOException ioe){
//			 System.out.println("Error opening thread: " + ioe);
//		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
         }
         System.out.println("finish add thread");
	  }
      else
         System.out.println("Client refused: maximum " + clients.length + " reached.");
   }

   private int findClient(String operator)
   {
       int ID = -1;
      
         String o = (hashmap.get(operator)).toString();
         ID = Integer.parseInt(o);
       
	 for (int i = 0; i < clientCount; i++)
         if (clients[i].getID() == ID)
            return i;
      return -1;

   }

   // send message to server   called when click enter.  
   //protected void sendTextToChat(String message)
   protected synchronized void sendTextToChat(String operator,PassClass pc)
   {
      AgentThread c = clients[findClient(operator)];
      c.send(operator,pc);
      System.out.println("client broadcast");
   }
   
   
   public Date stringToDate (String sd)
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        Date myDate = null;
       try { 
            myDate = (Date)formatter.parse(sd);
            return myDate;
       } catch (ParseException ex) {
           Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }
   
// set jtable for shortest Time
   public void showShortestTimeRoutes()
   {
        frame.model= (javax.swing.table.DefaultTableModel)frame.jTable1.getModel();
        
        frame.model.setColumnCount(0);
         frame.model.setRowCount(0);
               

                frame.model.addColumn("Depart");
                frame.model.addColumn("Destination");
                frame.model.addColumn("Dep_time");
                frame.model.addColumn("Arr_time");
                frame.model.addColumn("Price €");
                frame.model.addColumn("Available");
                frame.model.addColumn("Operator");
                frame.model.addColumn("ticket_id");


                
               for (int i = 0;i<shortTimeList.size();i++)
               {
                   RouteTimePrice rtp = shortTimeList.get(i);
                   SearchResult sr1 = rtp.sr1;
                   SearchResult sr2 = rtp.sr2;
                                      
                   frame.appendText("\n"+ sr1.toString());
                   frame.model.addRow(new Object[] {sr1.dep,sr1.arr,sr1.dep_time,sr1.arr_time,sr1.price,sr1.avai,sr1.operator,sr1.ticket_id});  
                   
                   if (sr2 != null)
                   {
                       frame.appendText("\n"+ sr2.toString());
                        frame.model.addRow(new Object[] {sr2.dep,sr2.arr,sr2.dep_time,sr2.arr_time,sr2.price,sr2.avai,sr2.operator,sr2.ticket_id});  
                   }
               }
   }
   
   
   // set jtable for shortest Time
   public void showLowestPriceRoutes()
   {
        frame.model= (javax.swing.table.DefaultTableModel)frame.jTable1.getModel();
        
        frame.model.setColumnCount(0);
         frame.model.setRowCount(0);
               

                frame.model.addColumn("Depart");
                frame.model.addColumn("Destination");
                frame.model.addColumn("Dep_time");
                frame.model.addColumn("Arr_time");
                frame.model.addColumn("Price €");
                frame.model.addColumn("Available");
                frame.model.addColumn("Operator");
                frame.model.addColumn("ticket_id");


                
               for (int i = 0;i<lowPriceList.size();i++)
               {
                   RouteTimePrice rtp = lowPriceList.get(i);
                   SearchResult sr1 = rtp.sr1;
                   SearchResult sr2 = rtp.sr2;
                                      
                   frame.appendText("\n"+ sr1.toString());
                   frame.model.addRow(new Object[] {sr1.dep,sr1.arr,sr1.dep_time,sr1.arr_time,sr1.price,sr1.avai,sr1.operator,sr1.ticket_id});  
                   
                   if (sr2 != null)
                   {
                       frame.appendText("\n"+ sr2.toString());
                        frame.model.addRow(new Object[] {sr2.dep,sr2.arr,sr2.dep_time,sr2.arr_time,sr2.price,sr2.avai,sr2.operator,sr2.ticket_id});  
                   }
               }
   }
   
   
   
//    client receive the response from server, deal with the response. (show data on UI)
   public synchronized void handle(int ID,PassClass pc)
   {
	 
	  frame.appendText("\nID : "+ ID + pc.toString());
	
	   if (pc.content.equals("route"))
	   {
		 try {
			   CallableStatement insert_route = conn.prepareCall("{call agent.insert_route(?,?)}");
			   
			   for (int i = 0; i< pc.al_route.size(); i++) 
			   {
				   Route c = pc.al_route.get(i);
				   try {
					   insert_route.setString(1, c.dep);
					   insert_route.setString(2, c.arr);
					   insert_route.execute();
				   }
				   catch (Exception e)
				   {
					   //gui.output.append(c.toString());
					   //e.printStackTrace();
				   }
				   //gui.output.append(c.toString());
				   frame.appendText(c.toString());
			   }
			   //insert_city.close();

		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		 
		 //		   // bind data to combox
		   try {
			   bindComBox();
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
                 hashmap.put(pc.operator,ID);        // remember how many operator connect.
                 
	   }

           // new ///////////////////////////////////////
	   if (pc.content.equals("SearchResult"))
	   {
                  directly = new ArrayList<SearchResult>();
                 step1 = new ArrayList<SearchResult>();
                step2 = new ArrayList<SearchResult>();
               
                frame.model= (javax.swing.table.DefaultTableModel)frame.jTable1.getModel();
//                frame.model.setColumnCount(0);
//                frame.model.setRowCount(0);
                frame.model.addColumn("Depart");
                frame.model.addColumn("Destination");
                frame.model.addColumn("Dep_time");
                frame.model.addColumn("Arr_time");
                frame.model.addColumn("Price €");
                frame.model.addColumn("Available");
                frame.model.addColumn("Operator");
                frame.model.addColumn("ticket_id");


                
               for (int i = 0;i<pc.al_sr.size();i++)
               {
                   SearchResult sr = pc.al_sr.get(i);
                   frame.appendText("\n"+ sr.toString());
                   frame.model.addRow(new Object[] {sr.dep,sr.arr,sr.dep_time,sr.arr_time,sr.price,sr.avai,sr.operator,sr.ticket_id});            
               }
               
               // try to put all 2 step routes into 2 list 
               // start -> mid
               // mid -> end
               
               // put all onr step route into sortList. RouteTimePrice list
               sortList = new ArrayList<RouteTimePrice>();
               for (int z = 0;z<finalList.size();z++)
              {
                   for (int i = 0;i<pc.al_sr.size();i++)
                   {
                       SearchResult sr = pc.al_sr.get(i);
                              ArrayList al = finalList.get(z);
                              if (al.size() == 2)
                              {
                                  String dep = (String)al.get(0);
                                  String arr = (String)al.get(1);

                                  if (sr.dep.equals( dep) && sr.arr.equals(arr))
                                  {
                                    directly.add(sr);
                                    
                                    Date sr1Arr = stringToDate (sr.arr_time);
                                    Date sr1Dep = stringToDate (sr.dep_time);
                                    long time = sr1Arr.getTime() - sr1Dep.getTime();
                                    
                                    RouteTimePrice rtp = new RouteTimePrice(time,sr.price,sr,null);
                                    sortList.add(rtp);
                                  }

                              }
                              if (al.size() == 3)
                              {

                                  String dep = (String)al.get(0);
                                  String mid = (String)al.get(1);
                                  String arr = (String)al.get(2);

                                  if (sr.dep.equals( dep) && sr.arr.equals(mid))
                                  {
                                      step1.add(sr);
                                  }
                                  else if (sr.dep.equals(mid) && sr.arr.equals(arr))
                                  {
                                      step2.add(sr);
                                  }
                              }
                           }
                      }
                  //notDirectly = new ArrayList<ArrayList<SearchResult>>();
                  //sortList = new ArrayList<RouteTimePrice>();
                  // get all possible to the list (two step)
                  for (int l = 0;l < step1.size();l++)
                  {
                      SearchResult sr1 = step1.get(l);
                      Date sr1Arr = stringToDate (sr1.arr_time);
                      Date sr1Dep = stringToDate (sr1.dep_time);
                       
                      for (int m = 0;m<step2.size();m++)
                      {
                          SearchResult sr2 = step2.get(m);
                          Date sr2Dep = stringToDate (sr2.dep_time);
                          Date sr2Arr = stringToDate (sr2.arr_time);
                          
                          if (sr1.arr.equals(sr2.dep) && sr1Arr.compareTo(sr2Dep)<=0)
                          {
                              long time = sr2Arr.getTime() - sr1Dep.getTime();
                              double totalPrice = sr1.price + sr2.price;
                              
                              RouteTimePrice rtp = new RouteTimePrice(time,totalPrice,sr1,sr2);
                              sortList.add(rtp);
                          }
                      }
                  }
                  
                  if (sortList.size() != 0)
                  {
                      // final short time list, low price list
                       shortTimeList = new  ArrayList<RouteTimePrice> ();
                       lowPriceList = new  ArrayList<RouteTimePrice> ();

//                      shortTimeList.add(sortList.get(0));
//                      lowPriceList.add(sortList.get(0));
                      
                      for(int n = 0; n< sortList.size();n++)
                      {
                          RouteTimePrice rtp = sortList.get(n);
                          if (shortTimeList.size() == 0)
                          {
                              shortTimeList.add(rtp);
                          }
                          else
                          {
                              if (rtp.time < shortTimeList.get(0).time)
                              {
                                  shortTimeList.clear();
                                  shortTimeList.add(rtp);
                              }
                              if  (rtp.time == shortTimeList.get(0).time)
                              {
                                   shortTimeList.add(rtp);
                              }
                              
                          }
                          
                          if (lowPriceList.size() == 0)
                          {
                              lowPriceList.add(rtp);
                          }
                          else
                          {
                              if (rtp.price < lowPriceList.get(0).price)
                              {
                                  lowPriceList.clear();
                                  lowPriceList.add(rtp);
                              }
                              if  (rtp.price == lowPriceList.get(0).price)
                              {
                                   lowPriceList.add(rtp);
                              }
                              
                          }
                      }
                  }
               
	   }
           ///////////////////////////////////////////////////////

            if (pc.content.equals("bookResult"))
            {
                returnCount--;
                if (pc.bookId > 0)
                {
                    bookReturnList.add(new BookReturn(pc.operator,pc.bookId));
                    System.out.println("put into hashmap book id : " + pc.bookId );
                }
                else if (pc.bookId == 0)
                {
                    frame.appendText("\n ticket has been booked");
                }
                else
                {
                    frame.appendText("\n unexpect error : connect or database.");
                }
                
                System.out.println("--------========");
            }

   }


   public void stop()
   {
      try
      {  if (console   != null)  console.close();
         //if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close();
      }
      catch(IOException ioe)
      {
		  System.out.println("Error closing ...");
      }
      clientT.close();
      thread = null;
   }
   
   public void buildMap() throws Exception
   {   
       Statement s1 = conn.createStatement();
       s1.executeQuery("SELECT distinct dep_city FROM agent.route r");
       ResultSet rs1 = s1.getResultSet();
 
       nl = new ArrayList<Node>();
       g=new Graph();
       
       while (rs1.next())
       {
    	   String a = rs1.getString(1);
    	   //char a = (char)(rs1.getInt(1));
    	   Node n = new Node(a);
    	   nl.add(n);
    	   g.addNode(n);
       }
       rs1.close ();
       
	   Statement s = conn.createStatement ();
       s.executeQuery ("SELECT * FROM agent.route r");
       ResultSet rs = s.getResultSet ();
       int count = 0;
       //ArrayList <Route> set = new  ArrayList <Route>();
       while (rs.next ())
       {
    	   String dep_id = rs.getString(1);
    	   String des_id = rs.getString(2);
//    	   char dep = (char) dep_id;
//    	   char des = (char) des_id;
    	   
    	   int a = -1;
    	   int b = -1;
    	   
    	   for (int i = 0; i<nl.size();i++)
    	   {
    		   Node c = nl.get(i);
    		   if (c.label.equals(dep_id))
    		   {
    			   a = i;
    		   }
    		   if (c.label.equals(des_id))
    		   {
    			   b = i;
    		   }  			      
    	   }
    	   g.connectNode(nl.get(a),nl.get(b));
    	 
       }
       rs.close ();
       //s.close ();
   }
   
   // called by button click
   public void searchRoute(String fi, String la)
   {
	   finalList = null;
	   int a = -1;
	   int b = -1;
	   
	   for (int i = 0; i<nl.size();i++)
	   {
		   Node c = nl.get(i);
		   if (c.label.equals(fi))
		   {
			   a = i;
		   }
		   if (c.label.equals(la))
		   {
			   b = i;
		   }  			      
	   }
	   
	   g.setRootNode(nl.get(a));
	   g.setLastNode(nl.get(b));
	   
	   g.bfs();
	   finalList = g.finalList;
	  
   }
   
   public PassClass getSearchObj(String s)
   {
        String DateStr = s;
	ArrayList <SearchParam> set = new  ArrayList <SearchParam>();
      for (int i = 0;i<finalList.size();i++)
      {
    	  ArrayList al = finalList.get(i);
    	  if (al.size() == 2)
    	  {
    		  String dep = (String)al.get(0);
        	  String arr = (String)al.get(1);
        	  SearchParam sp = new SearchParam(s,dep,arr);
        	  set.add(sp);
    	  }
    	  if (al.size() == 3)
    	  {
    		  String dep = (String)al.get(0);
    		  String mid = (String)al.get(1);
    		  String arr = (String)al.get(2);
    		  set.add(new SearchParam(s,dep,mid));
    		  set.add(new SearchParam(s,mid,arr));
    	  }
       }
       pc = new PassClass ("search","agent");  // pack up for send
       pc.al_sp = set;
       for (int i = 0;i<set.size();i++)
       {
    	   SearchParam sp = set.get(i);
    	   frame.appendText(sp.toString());
    	   System.out.print(sp.toString());
       }
       return pc;
   }

   public synchronized ArrayList <BookReturn> agent_cancel (String setTicket_id)
   {
      
        ArrayList <BookReturn> al = new  ArrayList <BookReturn>();
        try {
                CallableStatement get_agent_booked_ticket = conn.prepareCall("{CALL agent.get_agent_booked_ticket(?)}");
                get_agent_booked_ticket.setString(1, setTicket_id);
                boolean hadResult1 = get_agent_booked_ticket.execute();
                ResultSet rs1 = get_agent_booked_ticket.getResultSet();

                while (rs1.next()) {
                    String operator = rs1.getString(1);
                    int book_id = rs1.getInt(2);
                    BookReturn br = new BookReturn(operator,book_id);
                    al.add(br);
                    System.out.println("operator: " + operator +" "+ "book_id : " + book_id);
                }
                rs1.close();
                
                    CallableStatement cancel = conn.prepareCall("{CALL agent.agent_cancel(?)}");
                    cancel.setString(1, setTicket_id);
                    boolean hadResult = cancel.execute();


                return al;

                } catch (SQLException ex) {

                       // TODO Auto-generated catch block
                       Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
                }
        return null;
   }

    public synchronized void agent_book(String operator,int bookid,String setTicket_id)
   {
        try {
               CallableStatement book = conn.prepareCall("{CALL agent.agent_book(?,?,?)}");

               book.setString(1,operator);
               book.setInt(2, bookid);
               book.setString(3, setTicket_id);

               boolean hadResult = book.execute();


           } catch (Exception e) {

                   // TODO Auto-generated catch block
                   e.printStackTrace();
           }
   }
   public void bindComBox() throws SQLException
   {
       frame.jComboBox1.removeAllItems();
       frame.jComboBox2.removeAllItems();
       frame.jComboBox3.removeAllItems();

       Statement s = conn.createStatement ();
       s.executeQuery ("SELECT date(D) FROM agent.dd d;");
       ResultSet rs = s.getResultSet ();
       int count = 0;
       //ArrayList <Route> set = new  ArrayList <Route>();
       while (rs.next ())
       {
    	   String dateStr = rs.getString(1);
    	   frame.jComboBox1.addItem(dateStr);
       }
       
       Statement s1 = conn.createStatement ();
       s1.executeQuery ("SELECT distinct dep_city FROM agent.route r");
       ResultSet rs1 = s1.getResultSet ();
       //ArrayList <Route> set = new  ArrayList <Route>();
       while (rs1.next ())
       {
    	   String cityStr = rs1.getString(1);
    	   frame.jComboBox2.addItem(cityStr);
    	   frame.jComboBox3.addItem(cityStr);
       }
   }

   // remove client from client thread array,
   public synchronized void remove(String operator)
   {

	  int pos = findClient(operator);
      if (pos >= 0){
		 AgentThread toTerminate = clients[pos];
         System.out.println("Removing client thread " + operator);

         if (pos < clientCount-1)
            for (int i = pos+1; i < clientCount; i++)
               clients[i-1] = clients[i];
         clientCount--;

//         try{
			 toTerminate.close();
//	     }
//         catch(IOException ioe)
//         {
//			 System.out.println("Error closing thread: " + ioe);
//		 }
		 toTerminate = null;

		 notifyAll();
      }
   }

  

  
   public static void main(String args[])
   {  
	  Agent client = null;
//      if (args.length != 3)
//         System.out.println("Usage: java Agent host port name");
//      else
//         client = new Agent(args[0], Integer.parseInt(args[1]), args[2]);
          
          //
          //client = new Agent("localhost", 1234, "xu");
          client = new Agent();
          //client = new

   }
   
   
}
