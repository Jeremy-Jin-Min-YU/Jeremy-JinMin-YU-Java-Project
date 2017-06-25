

import java.io.Serializable;

public class Train implements Serializable{
	public String train_id;
	public int seat;
	
	public Train (String s,int i)
	{
		train_id = s;
		seat = i;
	}
	
	public String toString()
	{
		String s = "\nTrain id: " + train_id + ", seat: " + seat; 
		return s;
	}
}
