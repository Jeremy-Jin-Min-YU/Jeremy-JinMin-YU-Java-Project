

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Graph 
{
	ArrayList<Node> route = new ArrayList<Node>();
	public Node rootNode;
	public Node lastNode;
	public ArrayList nodes=new ArrayList();
	public int[][] adjMatrix;//Edges will be represented as adjacency Matrix
	int size;
	public ArrayList <ArrayList>finalList = null;
	public ArrayList <ArrayList>interList = new ArrayList <ArrayList>();
	
	//public Object[] objArr = null;
	
	public void setRootNode(Node n)
	{
		this.rootNode=n;
	}
	
	public void setLastNode(Node n)
	{
		this.lastNode = n;
	}
	
	public Node getRootNode()
	{
		return this.rootNode;
	}
	
	public void addNode(Node n)
	{
		nodes.add(n);
	}
	
	//This method will be called to make connect two nodes
	public void connectNode(Node start,Node end)
	{
		if(adjMatrix==null)
		{
			size=nodes.size();
			adjMatrix=new int[size][size];
		}

		int startIndex=nodes.indexOf(start);
		int endIndex=nodes.indexOf(end);
		adjMatrix[startIndex][endIndex]=1;
		adjMatrix[endIndex][startIndex]=1;
	}
	
	private Node getUnvisitedChildNode(Node n)
	{
		
		int index=nodes.indexOf(n);
		int j=0;
		while(j<size)
		{
			if(adjMatrix[index][j]==1 && ((Node)nodes.get(j)).visited==false)
			{
				return (Node)nodes.get(j);
			}
			j++;
		}
		return null;
	}
	
	//BFS traversal of a tree is performed by the bfs() function
	public void bfs()
	{
		
		//BFS uses Queue data structure
		Queue <ArrayList<Node>> q=new LinkedList<ArrayList<Node>>();
		rootNode.visited=true;
		route.add(this.rootNode);
		q.add(route);
		//printNode(this.rootNode);
		
		while(!q.isEmpty())
		{
			lastNode.visited = false;
			ArrayList<Node> r = (ArrayList<Node>)q.remove();
			
			interList.add(r);
			//Node n=(Node)q.remove();
			Node n = (Node)r.get(r.size()-1);
			
			Node child=null;
			while((child=getUnvisitedChildNode(n))!=null )
			{
				ArrayList<Node> al = new ArrayList<Node>();
				for (int i=0;i<r.size();i++)
				{
					al.add((Node)r.get(i) );
				}
				child.visited=true;
				//printNode(child);
//				for (int i = 0;i<al.size();i++)
//				{
//					Node c = (Node)(al.get(i));
//					System.out.print(c.label + " ");  (ArrayList<Node>[])
//				}
				
				al.add(child);
				q.add(al);
				
				//objArr = q.toArray();
				
//				for (int i = 0;i<q.size();i++)
//				{
//					ArrayList<Node> aa = (ArrayList<Node>) objArr[i];
//					for (int m = 0; m < aa.size();m++)
//					{
//						Node c = (Node)(aa.get(m));
//						System.out.print(c.label + " ");
//					}
//					System.out.print("\n");
//				}
//				System.out.print("\n ++++++++++++++ \n");
			}
		}
		//Clear visited property of nodes
		clearNodes();
		
//		System.out.print("\n 88888888 \n");
//		for (int i = 0;i<objArr.length;i++)
//		{
//			ArrayList<Node> aa = (ArrayList<Node>) objArr[i];
//			for (int m = 0; m < aa.size();m++)
//			{
//				Node c = (Node)(aa.get(m));
//				System.out.print(c.label + " ");
//			}
//			System.out.print("\n");
//		}
//		System.out.print("\n ++++++++++++++ \n");
		
		
		finalList = new ArrayList <ArrayList>();

		for (int i = 0;i<interList.size();i++)
		{
			boolean fn = false;
			boolean ln = false;
			ArrayList<Node> aa = (ArrayList<Node>) interList.get(i);
			
			if (aa.size() > 3)
				continue;
			
			ArrayList intList = new ArrayList();
			for (int m = 0; m < aa.size();m++)
			{
				Node n = aa.get(m);
				intList.add(n.label);
//				if (m == 0)
//				{
					if (n.label.equals(this.rootNode.label))
					{
						fn = true;
					}
//				}
				if (m == aa.size()-1)
				{
					if (n.label.equals(this.lastNode.label))
					{
						ln = true;
					}
				}
				
			}
			if (fn && ln)
			{
				finalList.add(intList);
			}
		}

		System.out.print("\n xxxxxxxxxxxxxxxx " + this.size);
		
		System.out.print("\n" );

		for (int i = 0;i<finalList.size();i++)
		{
			ArrayList aa =  finalList.get(i);
			for (int m = 0; m < aa.size();m++)
			{
				System.out.print(aa.get(m) + " ");
			}
			System.out.print("\n");
		}
	}
		
		

	
//	public void bfs()
//	{
//		
//		//BFS uses Queue data structure
//		Queue q=new LinkedList();
//		q.add((this.rootNode));
//		printNode(this.rootNode);
//		rootNode.visited=true;
//		while(!q.isEmpty())
//		{
//			Node n=(Node)q.remove();
//			Node child=null;
//			while((child=getUnvisitedChildNode(n))!=null)
//			{
//				child.visited=true;
//				printNode(child);
//				q.add(child);
//			}
//		}
//		//Clear visited property of nodes
//		clearNodes();
//	}
	
	//DFS traversal of a tree is performed by the dfs() function
	public void dfs()
	{
		//DFS uses Stack data structure
		Stack s=new Stack();
		s.push(this.rootNode);
		rootNode.visited=true;
		printNode(rootNode);
		while(!s.isEmpty())
		{
			Node n=(Node)s.peek();
			Node child=getUnvisitedChildNode(n);
			if(child!=null)
			{
				child.visited=true;
				printNode(child);
				s.push(child);
			}
			else
			{
				s.pop();
			}
		}
		//Clear visited property of nodes
		clearNodes();
	}
	
	
	//Utility methods for clearing visited property of node
	private void clearNodes()
	{
		int i=0;
		while(i<size)
		{
			Node n=(Node)nodes.get(i);
			n.visited=false;
			i++;
		}
	}
	
	//Utility methods for printing the node's label
	private void printNode(Node n)
	{
		System.out.print(n.label+" ");
	}

	
	
	

}
