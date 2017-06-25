

import java.io.Serializable;

public class Ticket implements Serializable{
	
	public int ticket_id;
	public String dep_time ; 
	public String arr_time;
	public String route_route_id;
	
	public Ticket (int i, String s,String s1,String s2)
	{
		ticket_id = i;
		dep_time = s;
		arr_time = s1;
		route_route_id = s2;
		
	}
	
	public String toString()
	{
		String s = "\n"+ticket_id + ", " + dep_time + ", " + arr_time + ", " +route_route_id; 
		return s;
	}
	
}
