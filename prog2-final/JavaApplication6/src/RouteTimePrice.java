/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zexu
 */
public class RouteTimePrice {
    public long time; 
    public double price;
    public SearchResult sr1 = null;
    public SearchResult sr2 = null;
    
    public RouteTimePrice (long t,double p,SearchResult s1,SearchResult s2)
    {
        time = t;
        price = p;
        sr1 = s1;
        sr2 = s2;
    }
}
