


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		//Lets create nodes as given as an example in the article
		Node nA=new Node("1");
		Node nB=new Node("2");
		Node nC=new Node("3");
		Node nD=new Node("4");
		Node nE=new Node("5");
		//Node nF=new Node("6");

		//Create the graph, add nodes, create edges between nodes
		Graph g=new Graph();
		g.addNode(nA);
		g.addNode(nB);
		g.addNode(nC);
		g.addNode(nD);
		g.addNode(nE);
		//g.addNode(nF);
		g.setRootNode(nA);
		g.setLastNode(nE);
		
		g.connectNode(nA,nB);
		g.connectNode(nA,nC);
		g.connectNode(nA,nD);
		
		g.connectNode(nB,nE);
		g.connectNode(nC,nE);
		g.connectNode(nD,nE);
		
		g.connectNode(nB,nC);
		g.connectNode(nC,nD);
		/////////////////////////
		g.connectNode(nB,nA);
		g.connectNode(nC,nA);
		g.connectNode(nD,nA);
		
		g.connectNode(nE,nB);
		g.connectNode(nE,nC);
		g.connectNode(nE,nD);
		
		g.connectNode(nC,nB);
		g.connectNode(nD,nC);
		
		
		//Perform the traversal of the graph
		System.out.println("DFS Traversal of a tree is ------------->");
		//g.dfs();
		
		System.out.println("\nBFS Traversal of a tree is ------------->");
		g.bfs();
		
		
		
		
	}

}
