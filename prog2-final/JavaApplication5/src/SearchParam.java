

import java.io.Serializable;
public class SearchParam  implements Serializable {

	public String DateStr;
	public String dep;
	public String arr;
	
	SearchParam (String s, String s1,String s2)
	{
		DateStr = s;
		dep = s1;
		arr = s2;
	}
	
	public String toString()
	{
		String s = "\n"+DateStr + ", "+ dep + ", " + arr ; 
		return s;
	}
}
