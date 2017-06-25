
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ze xu
 */
public class NewClass {
 public static void main(String args[])
{

     HashMap       hashmap       =       new       HashMap();
     hashmap.put("operator1", 1234);
     String s = hashmap.get("operator1").toString();
     System.out.println(s);
}

}
