

import java.io.Serializable;

public class City implements Serializable{
	public int city_id;
	public String city_name ; 
	
	City (int i, String s)
	{
		city_id = i;
		city_name = s;
	}
	
	public String toString()
	{
		String s = "\ncity id: " + city_id + ", city name: " + city_name; 
		return s;
	}
	
}
