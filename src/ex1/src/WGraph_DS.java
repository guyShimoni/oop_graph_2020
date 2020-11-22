package ex1.src;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;



public class WGraph_DS implements weighted_graph {

	private HashMap<Integer, node_info> hash_node;
	private int edgeSize;
	private int nodeSize;
	private int MC;

	// node string: [the node info=%neighbor, weight, %]

	//Constructor
	public WGraph_DS(){
		this.hash_node=new HashMap<>();	

		this.nodeSize = 0;
		this.edgeSize = 0;
		this.MC = 0;

	}


	/**
	 * return the node with the input key
	 *  with the use of the HashMap within O(1) running time complexity.
	 *  @param key - the node_id
	 *  @return the node_data by the key
	 * */

	@Override
	public node_info getNode(int key) {

		return this.hash_node.get(key); 
	}

	/**
	 * the method run in O(1) time.
	 * return true if only  there is an edge between node1 and node2
	 * @param node1- node_id
	 * @param node2- node_id
	 */

	@Override
	public boolean hasEdge(int node1, int node2) {
		if (this.hash_node.get(node1) == null || this.hash_node.get(node2) == null){

			return false;
		}
		if(this.getNode(node1).getInfo().indexOf("%"+node2+":")==-1){
			return false;
		}
		return true;
	}

	/**
	 * return the weight of the edge.
	 * In case there is no such edge : return -1
	 * run in O(1) time.
	 *
	 * @param node1 - a key
	 * @param node2 - a key
	 * @return the weight of the edge.
	 */

	@Override
	public double getEdge(int node1, int node2) {
		if (!hasEdge(node1, node2)) return -1;
		int index=this.getNode(node1).getInfo().indexOf("%"+node2+":");
		int index2=this.getNode(node1).getInfo().indexOf(":",index);
		int index3=this.getNode(node1).getInfo().indexOf("%",index2);
		return Double.parseDouble(this.getNode(node1).getInfo().substring(index2+1, index3));
	}


	/**
	 * add a new node into this weighted graph .
	 * this method run in O(1) time.
	 * @param key
	 */


	@Override
	public void addNode(int key) {

		node_info n = new NodeInfo(key, 0, "%");
		if(WGraph_Algo.INFO!="")
			n.setInfo(WGraph_Algo.INFO);
		if(WGraph_Algo.TAG!=0){
			n.setTag(WGraph_Algo.TAG);
		}



		this.hash_node.put(key, n);
		this.nodeSize++;
		MC++;


	}

	/**
	 *  Connect an edge between node1 and node2, with an edge with weight >=0.
	 *  this method run in O(1) time.
	 *  @param node1 - the source Node's key.
	 *  @param node2 - the destination Node's key.
	 *  @param w - the weight of the edge between the two 
	 */


	@Override
	public void connect(int node1, int node2, double w) {
		if (node1 == node2) 
		{
			System.out.println("node 1 == node 2");
			return;
		}
		if(!this.hash_node.containsKey(node1)||!this.hash_node.containsKey(node2)){
			System.out.println("The vertices are not contained in the graph");
			return;
		}
		if(w < 0){
			throw new RuntimeException();
		}
		if(hasEdge(node1,node2)){
			return;
		}
		node_info n1=this.getNode(node1);
		node_info n2=this.getNode(node2);
		n1.setInfo(n1.getInfo()+node2+":"+w+"%");
		n2.setInfo(n2.getInfo()+node1+":"+w+"%");

		edgeSize++;
		MC++;

	}

	/**
	 * return a Collection of all the vertices in this weighted graph
	 * this method run in O(1) time.
	 * @param hash_node- return the vertices Collection
	 * @return Collection<node_data>
	 */


	@Override
	public Collection<node_info> getV() {

		return this.hash_node.values();
	}
	/**
	 * return a Collection of all the nodes connected to node_id
	 * @paran node_id
	 * @return Collection<node_info>
	 */

	@Override
	public Collection<node_info> getV(int node_id) {

		Collection<node_info> n = new ArrayList<node_info>();
		//System.out.println(this.getNode(node_id).getInfo());
		StringTokenizer separ=new StringTokenizer(this.getNode(node_id).getInfo(), "%:");
		int i=0;

		while(separ.hasMoreTokens()){
			String s=separ.nextToken();
			//System.out.println(s);
			if(i%2==0){
				n.add(this.getNode(Integer.parseInt(s)));
			}
			i++;

		}
		return n;
	}

	/**
	 * delete the node with the given key from the graph.
	 * @return temp- the data of the removed node (null if none).
	 * @param key- the node to be deleted key.
	 */

	@Override
	public node_info removeNode(int key) {

		node_info temp = getNode(key);
		LinkedList<Integer> list= new LinkedList<Integer>();
		if(hash_node.containsKey(key))
		{
			for (node_info neighbor : getV(key)) 
			{
				list.add(neighbor.getKey());
			}
			while(!list.isEmpty()){
				removeEdge(key, list.poll());
			}
			this.hash_node.remove(key);
			//has_ni.get(key).clear();
			nodeSize--;
			MC++;
		}

		return temp;
	}


	/**
	 * Delete the edge from the graph,run in O(1) time
	 * @param node1- key node1
	 * @param node2- key node2
	 */

	@Override
	public void removeEdge(int node1, int node2) {

		if (!this.hash_node.containsKey(node1)||!this.hash_node.containsKey(node2)||!hasEdge(node1, node2)){
			//System.out.println("55");
			return;
		}
		else{
			int remove1=this.getNode(node1).getInfo().indexOf("%"+node2+":");
			int remove11=this.getNode(node1).getInfo().indexOf(":",remove1);
			int remove111=this.getNode(node1).getInfo().indexOf("%",remove11);
			int remove2=this.getNode(node2).getInfo().indexOf("%"+node1+":");
			int remove22=this.getNode(node2).getInfo().indexOf(":",remove2);
			int remove222=this.getNode(node2).getInfo().indexOf("%",remove22);
			this.getNode(node1).setInfo(this.getNode(node1).getInfo().substring(0, remove1)+this.getNode(node1).getInfo().substring(remove111));
			this.getNode(node2).setInfo(this.getNode(node2).getInfo().substring(0, remove2)+this.getNode(node2).getInfo().substring(remove222));
			edgeSize--;
			MC++;
		}
	}


	/**
	 *  the method run in O(1) time.
	 * @return number of vertices (nodes) in the graph
	 */

	@Override
	public int nodeSize() {
		return hash_node.size();
	}

	/**
	 * the method run in O(1) time.
	 * @return the number of edges in the graph
	 */

	@Override
	public int edgeSize() {
		return edgeSize;
	}

	/**
	 * returns the Mode Count(MC) number of the graph.
	 * @param MC- the Mode Count
	 */

	@Override
	public int getMC() {
		return MC;
	}
	

	   @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        WGraph_DS that = (WGraph_DS) o;

	        if (MC != that.MC) return false;
	        if (edgeSize != that.edgeSize) return false;
	        if (!hash_node.equals(that.hash_node)) return false;
	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int result = hash_node.hashCode();
	        result = 31 * result + MC;
	        result = 31 * result + edgeSize;
	        return result;
	    }

	public class NodeInfo implements  node_info,Comparable<node_info>{

		//NodeInfo Parameters:
		private int _key;
		private double _tag;
		private int countNode;
		private String _info;
		//private HashMap<Integer,node_info> has_map;


		//Constructor:
		public NodeInfo(){
			//this.has_map = new HashMap<Integer,node_info>();
			this._info = "%";
			this._tag = 0;
			this._key = countNode++;

		}
		public NodeInfo(int key, double tag, String info) {
			_key = key;
			_tag = tag;
			_info = info;
		}


		@Override
		public int getKey() {
			return this._key;
		}

		@Override
		public String getInfo() {
			return this._info;
		}

		@Override
		public void setInfo(String s) {
			this._info=s;

		}

		@Override
		public double getTag() {
			return this._tag;
		}

		@Override
		public void setTag(double t) {
			this._tag=t;

		}


		public String toString(){
			String s= "" + _key+" tag="+_tag+" info="+_info;
			return s;
		}
		@Override
		public int compareTo(node_info o) {

			if(this.getTag()-o.getTag()>0)
				return 1;
			else if(this.getTag()-o.getTag()==0)
				return 0;
			else
				return -1;
		}
		
		@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeInfo nodeInfo = (NodeInfo) o;

            return _key == nodeInfo._key;
        }
        @Override
        public int hashCode() {
            return _key;
        }
	}


	public static void main(String[] args) {
		WGraph_DS g=new WGraph_DS();
		WGraph_DS g1=new WGraph_DS();
		//g.addNode(0);
		assertEquals(g,g1);
		System.out.println(g.equals(g1));
//		g.addNode(0);
//		g.addNode(1);
//		g.addNode(2);
//		g.addNode(3);
//		g.connect(3, 1, 5.5);
//		g.connect(0, 2, 18);
//		System.out.println(g.getV());
//		g.connect(0, 1, 6);
//		System.out.println(g.getV());
//		node_info n=g.getNode(0);
//		System.out.println(n);
//		System.out.println(g.hasEdge(0, 1));
//		System.out.println(g.hasEdge(2, 1));
//		System.out.println(g.getEdge(0, 1));
//		System.out.println(g.getV());
//
//		g.removeEdge(0, 1);
//		System.out.println(g.getV());
//		g.nodeSize();
//		System.out.println(g.nodeSize());
//		g.edgeSize();
//		System.out.println(g.edgeSize());
	}

}
