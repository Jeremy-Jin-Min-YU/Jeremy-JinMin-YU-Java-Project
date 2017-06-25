

import java.io.Serializable;

public class Route implements Serializable{
	public String dep;
	public String arr;
	
	Route (String s1,String s2)
	{
		dep = s1;
		arr = s2;
	}
	
	public String toString()
	{
		String s = "\n"+ dep + ", " + arr ; 
		return s;
	}
	
}
