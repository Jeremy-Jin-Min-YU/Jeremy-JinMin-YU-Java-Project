


import java.io.*;

public class Items implements Serializable{
	String itemName;
	int price;
	
	public Items (String s,int p)
	{
		itemName = s;
		price = p;
	}
	
	public String getName()
	{
		return itemName;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public void setPrice(int p)
	{
		price = p;
	}
	
	public String toString()
	{
		return ("Item : " + itemName + " || Price : " + price);
	}
}
