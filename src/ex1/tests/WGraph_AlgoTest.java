package ex1.tests;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void isConnected() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
  
        g0 = WGraph_DSTest.graph_creator(1,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

         g0 = WGraph_DSTest.graph_creator(2,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());
        
         g0 = WGraph_DSTest.graph_creator(2,1,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        assertTrue(b);
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0,10);
        assertEquals(d, 5.1);
    }

    @Test
    void shortestPath() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,10);
        //double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
        int[] checkKey = {0, 1, 5, 7, 10};
        int i = 0;
        for(node_info n: sp) {
        	//assertEquals(n.getTag(), checkTag[i]);
        	assertEquals(n.getKey(), checkKey[i]);
        	i++;
        }
    }
    
    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }

    private weighted_graph small_graph() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(11,0,1);
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,3);

        g0.connect(1,4,17);
        g0.connect(1,5,1);
        g0.connect(2,4,1);
        g0.connect(3, 5,10);
        g0.connect(3,6,100);
        g0.connect(5,7,1.1);
        g0.connect(6,7,10);
        g0.connect(7,10,2);
        g0.connect(6,8,30);
        g0.connect(8,10,10);
        g0.connect(4,10,30);
        g0.connect(3,9,10);
        g0.connect(8,10,10);

        return g0;
    }
    
    
    @Test
	public void testWGraph_AlgoWeighted_graph() {
    	weighted_graph graph1 = new WGraph_DS();
		WGraph_Algo graph1Algo = new WGraph_Algo(graph1);
		assertEquals(graph1,graph1Algo.getGraph());

	}


	@Test
	public void testCopy() {
		weighted_graph graph1 = new WGraph_DS();
		WGraph_Algo graph1Algo = new WGraph_Algo(graph1);
		weighted_graph graph2 = graph1Algo.copy();
		boolean flag = true;
		for (int i=0;i<graph2.getV().size();i++)
		{
			if (graph2.getNode(i)!=graph1.getNode(i)) flag = false;
		}

		assertTrue(flag);

	}


	@Test
	public void testIsConnected() {
		weighted_graph graph1 = new WGraph_DS();

		WGraph_Algo graph1Algo= new WGraph_Algo(graph1);
		//boolean temp=true;
		assertTrue(graph1Algo.isConnected()); //cheking zero node
		graph1.addNode(0);
		assertTrue(graph1Algo.isConnected());
		graph1.addNode(1);
		assertFalse(graph1Algo.isConnected());
		graph1.connect(0,1,6);
		assertTrue(graph1Algo.isConnected());


	}



	@Test
	public void testShortestPathDist() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(0, 1, 6.1);
		g.connect(0, 2, 100.7);
		g.connect(2, 1, 4.1);
		WGraph_Algo ga=new WGraph_Algo();
		ga.init(g);
		Double n=ga.shortestPathDist(0,2);

		assertEquals(n, (Double)10.2);
	}

	@Test
	public void testShortestPath() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(0, 1, 6.1);
		g.connect(0, 2, 100.7);
		g.connect(2, 1, 4.1);
		WGraph_Algo ga=new WGraph_Algo();
		ga.init(g);
		assertEquals(ga.shortestPath(0, 2).toString(), "[0 tag=0.0 info=%1:6.1%2:100.7%, 1 tag=6.1 info=%0:6.1%2:4.1%, 2 tag=10.2 info=%0:100.7%1:4.1%]");
		assertEquals(ga.shortestPath(0, 3),null);
	}


	@Test
	public void testDijkstra() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.connect(0, 1, 6);
		//System.out.println(g.getV());
		WGraph_Algo ga=new WGraph_Algo();
		ga.init(g);
		ga.dijkstra(0);
		assertEquals(g.getV().toString(), "[0 tag=0.0 info=%1:6.0%, 1 tag=6.0 info=%0:6.0%, 2 tag=1.7976931348623157E308 info=%]");
	}
}