

import java.io.Serializable;

public class SearchResult implements Serializable{
	public String dep;
	public String arr;
	public String dep_time;
	public String arr_time;
	public double price;
	public int avai;
	public String operator;
        public int ticket_id;
	
	
	SearchResult (String s1,String s2,String s3,String s4,double s5,int s6,String s7,int t)
	{
		dep = s1;
		arr = s2;
		dep_time = s3;
		arr_time = s4;
		price = s5;
		avai= s6;
		operator = s7;
                ticket_id = t;
	}
	
	public String toString()
	{
		String s = "\n"+ dep + ", " + arr + ", " +dep_time +", " + arr_time +", " + price +", " + avai + ", " + operator ; 
		return s;
	}
	
}


