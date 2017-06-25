

import java.io.Serializable;
import java.util.ArrayList;

public class PassClass implements Serializable{
	public String content;      	// indicate the content you pass
	public String operator;		  		// which operator
	
	public ArrayList<Route> al_route   = new ArrayList<Route>();  
	public ArrayList<SearchParam> al_sp = new ArrayList<SearchParam>();
	public ArrayList<SearchResult> al_sr = new ArrayList<SearchResult>();
	public int bookTicketNo = 0;
        public int bookId = -1;
	
	PassClass (String con, String op)
	{
		content = con;
		operator = op;
	}
	
	public String toString()
	{
		String s = "\n"+content + ", " + operator;
		
		return s;
	}
}
