package ex1.src;

import java.io.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;






public class WGraph_Algo implements weighted_graph_algorithms {

	private weighted_graph _graph;
	public static double TAG=0;
	public static String INFO="";

	/**
	 * Default constructor.
	 * @param g - a weighted_graph
	 */
	public WGraph_Algo() {
		this._graph = new WGraph_DS();

	}

	public WGraph_Algo(weighted_graph g) {
		init(g);

	}

	/**
	 * initialization this graph from a given graph.
	 * @param g
	 */

	@Override
	public void init(weighted_graph g) {
		this._graph=g;


	}

	/**
	 * return the underlying graph of which this class works.
	 * @return this graph
	 */

	@Override
	public weighted_graph getGraph() {
		return this._graph;
	}

	/**
	 * return a deep copy of this weighted graph 
	 */

	@Override
	public weighted_graph copy() {
		WGraph_DS temp = new WGraph_DS();
		Collection<node_info> Nodes = _graph.getV();
		for (node_info Node : Nodes) {
			TAG=Node.getTag();
			INFO=Node.getInfo();
			temp.addNode(Node.getKey());
			TAG=0;
			INFO="";
		}
		return temp;
	}

	/**
	 * @return true- if there is a valid path from EVREY node to each
	 */

	@Override
	public boolean isConnected() {

		if(this._graph.nodeSize() ==0) return true;
		if(_graph ==null) return false;
		if(_graph.edgeSize()==0&& _graph.getV().size()>1) return false;
		if(_graph.getV().size()==0||_graph.getV().size()==1) return true;
		setTagZero(); 

		Iterator<node_info> iter = _graph.getV().iterator();
		Stack<node_info> st = new Stack<node_info>();
		LinkedList<node_info> q = new LinkedList<node_info>(); 

		node_info currentNode = iter.next();
		st.add(currentNode);
		currentNode.setTag(1);
		if(st.size() == 0 || st.size() == 1){                         
			return true;                                                     
		}
		node_info src = st.get(0);                                         
		q.add(src);

		while (!st.isEmpty()) {
			node_info current = st.pop();
			for (node_info i : _graph.getV(current.getKey())) {
				if (i.getTag() == 0) {
					st.add(i);
					i.setTag(1);

				}
			}
			current.setTag(2);
		}
		for (node_info i : _graph.getV()){
			if(i.getTag()==0){

				return false;
			}
		}


		return true;
	}



	/**
	 * returns the length the shortest path
	 * @param src - start node
	 * @param dest - end (target) node
	 */

	@Override
	public double shortestPathDist(int src, int dest) {



		if( _graph.getNode(src) == null || _graph.getNode(dest) == null) {
			return -1; // -1 = there is a path from src to dst
		}
		if(src==dest)
			return 0;

		dijkstra(src);
		double temp=_graph.getNode(dest).getTag();
		return temp == Integer.MAX_VALUE ? -1 : temp;

	}

	/**
	 *  returns the the shortest path between src to dest 
	 * @param src -  start node
	 * @param dest-	 end (target) node
	 */

	@Override
	public List<node_info> shortestPath(int src, int dest) {

		List<node_info> visit = new ArrayList<node_info>();
		int[] p=dijkstra(src);
		int run=dest;
		if(_graph.getNode(dest).getTag()!=Double.MAX_VALUE){
			while(run!=-1){
				visit.add(0,_graph.getNode(run));
				run=p[run%_graph.nodeSize()];
			}
			return visit;
		}
		return null;
	}



	/**
	 * Saves this weighted graph into a text file.
	 * @param file - the text file name.
	 * @return true - if the file was successfully saved, else return false.
	 */

	@Override
	public boolean save(String file) {
		try
		{
			FileOutputStream file1 = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(file1);
			out.writeObject(this._graph);
			out.close();
			file1.close();
			return true;
		}
		catch(IOException ex)
		{
			return false;
		}
	}	

	/**
	 * This method load a graph to this graph algorithm.
	 * if the file was successfully loaded - the underlying graph
	 * of this class will be changed (to the loaded one), in case the
	 * graph was not loaded the original graph should remain "as is".
	 * @param file - file name
	 * @return true - iff the graph was successfully loaded.
	 */

	@Override
	public boolean load(String file) {
		try {
			FileInputStream file1 = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(file1);
			_graph = (weighted_graph) in.readObject();
			in.close();
			file1.close();
			return true;
		}
		catch(IOException ex)
		{
			return false;
		}
		catch(ClassNotFoundException ex)
		{
			return false;
		}
	}



	/**
	 * dijkstra algorithm for more information : https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm.
	 * @param src - Integer that represent node key
	 */


	public int[] dijkstra(int src) {
		setTagInf();
		int[] prev=new int[_graph.nodeSize()];
		node_info srcS= _graph.getNode(src);
		Queue<node_info> queue = new LinkedBlockingQueue<node_info>();

		srcS.setTag(0);
		prev[src%_graph.nodeSize()]=-1;
		queue.add(srcS);
		while (!queue.isEmpty()) {
			node_info temp = queue.poll();
			Collection<node_info> ni = _graph.getV(temp.getKey()); 
			for(node_info nie : ni){
				if(temp.getTag()+_graph.getEdge(temp.getKey(), nie.getKey())<nie.getTag()){
					nie.setTag(temp.getTag()+_graph.getEdge(temp.getKey(), nie.getKey()));
					prev[nie.getKey()%_graph.nodeSize()]=temp.getKey();
					queue.remove(nie.getKey());
					queue.add(nie);
				}
			}
		}
		return prev;
	}

	private void setTagInf() {
		Collection<node_info> nodes = this._graph.getV();
		for (node_info n : nodes) {
			n.setTag(Double.MAX_VALUE);
		}
	}	


	/**
	 * private mithod- all the node in the graph be zero
	 */
	private void setTagZero() {
		Collection<node_info> nodes = this._graph.getV();
		for (node_info n : nodes) {
			n.setTag(0);
		}

	}

	/*public static void main(String[] args) {
		WGraph_DS g = new WGraph_DS();

		WGraph_Algo ga= new WGraph_Algo(g);
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		//g.addNode(4);
		//g.addNode(5);
		g.connect(0, 1, 4);
		g.connect(0, 2, 18);
		//g.connect(0, 1, 0);
		//g.connect(5, 2, 18);
		//g.connect(4, 5, 8);
		System.out.println(g.getV());

		System.out.println(ga.isConnected());

		System.out.println(ga.shortestPathDist(1, 2));
		ga.save("graph.txt");

		WGraph_DS g2 = new WGraph_DS();

		WGraph_Algo gb= new WGraph_Algo(g2);
		g2.addNode(0);
		g2.addNode(1);
		g2.addNode(2);

		System.out.println(g2.getV());

        gb.load("graph.txt");

        System.out.println(g2.getV());
	}*/

}
