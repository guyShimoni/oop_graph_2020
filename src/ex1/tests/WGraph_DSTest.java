package ex1.tests;

import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private static Random _rnd = null;

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(1);

        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        int s = g.nodeSize();
        assertEquals(1,s);

    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        double w03 = g.getEdge(0,3);
        double w30 = g.getEdge(3,0);
        assertEquals(w03, w30);
        assertEquals(w03, 3);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        Collection<node_info> v = g.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }
    }

    @Test
    void hasEdge() {
        int v = 10, e = v*(v-1)/2;
        weighted_graph g = graph_creator(v,e,1);
        for(int i=0;i<v;i++) {
            for(int j=i+1;j<v;j++) {
                boolean b = g.hasEdge(i,j);
                assertTrue(b);
                assertTrue(g.hasEdge(j,i));
            }
        }
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,1);
        assertFalse(g.hasEdge(1,0));
        g.removeEdge(2,1);
        g.connect(0,1,1);
        double w = g.getEdge(1,0);
        assertEquals(w,1);
    }


    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeNode(4);
        g.removeNode(0);
        assertFalse(g.hasEdge(1,0));
        int e = g.edgeSize();
        assertEquals(0,e);
        assertEquals(3,g.nodeSize());
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,3);
        double w = g.getEdge(0,3);
        assertEquals(w,-1);
    }


    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
    
    
    
    @Test
	public void testGetNode() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		node_info n1= g.getNode(0);
		node_info n2= g.getNode(3);

		assertEquals(n1.getKey(), 0);
		assertNull(n2);
	}

	@Test
	public void testHasEdge() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);

		g.connect(0, 1, 3.14);
		g.connect(0, 2, 1.2);
		assertTrue(g.hasEdge(1, 0));
		assertFalse(g.hasEdge(2, 1));
		assertFalse(g.hasEdge(0, 3));
		assertFalse(g.hasEdge(0, 0));

	}

	@Test
	public void testGetEdge() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);

		g.connect(0, 1, 3.14);
		g.connect(0, 2, 1.2);

		assertEquals(""+g.getEdge(1, 0),"3.14");
		assertEquals(""+g.getEdge(2, 1),"-1.0");
		assertEquals(""+g.getEdge(0, 3),"-1.0");
		assertEquals(""+g.getEdge(0, 0),"-1.0");

	}

	@Test
	public void testAddNode() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		assertNull(g.getNode(2));
		g.addNode(2);
		assertEquals(""+g.getNode(2).getKey(), "2");


	}

	@Test
	public void testConnect() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);

		g.connect(0, 1, 3.14);
		g.connect(0, 2, 1.2);
		g.addNode(2);
		g.connect(0, 2, 1.2);
		g.connect(0, 0, 1.2);
		assertTrue(g.hasEdge(1, 0));
		assertFalse(g.hasEdge(2, 1));
		assertFalse(g.hasEdge(0, 3));
		assertFalse(g.hasEdge(0, 0));
	}

	@Test
	public void testGetV() {


		weighted_graph g=new WGraph_DS();
		assertTrue(g.getV().isEmpty());
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);


		assertFalse(g.getV().isEmpty());
		g.addNode(4);
		assertNotEquals(4,g.getV());

	}



	@Test
	public void testRemoveNode() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(0, 1, 3.14);
		g.connect(0, 2, 1.2);
		g.addNode(2);
		g.connect(0, 2, 1.2);
		g.connect(0, 0, 1.2);
		g.removeNode(1);

		assertEquals(3,g.nodeSize());
		assertEquals(1,g.edgeSize());
		
		g.connect(3, 0, 3.14);
		assertEquals(3,g.nodeSize());
		assertEquals(2,g.edgeSize());
	}

	@Test
	public void testRemoveEdge() {
		weighted_graph g=new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		g.connect(0, 1, 3.14);
		g.connect(0, 2, 1.2);
		g.addNode(2);
		g.connect(0, 2, 1.2);
		g.connect(0, 0, 1.2);
		g.removeNode(1);
		g.removeEdge(0, 1);

		assertEquals(3,g.nodeSize());
		assertEquals(1,g.edgeSize());
		
		g.connect(3, 0, 3.14);
		assertEquals(3,g.nodeSize());
		assertEquals(2,g.edgeSize());
		
		g.removeEdge(2, 3);
		assertEquals(3,g.nodeSize());
		assertEquals(2,g.edgeSize());
		
	}

	@Test
	public void testNodeSize() {
		weighted_graph g=new WGraph_DS();
		assertEquals(0,g.nodeSize());
		
		g.addNode(0);
		g.addNode(1);
		assertEquals(2,g.nodeSize());
		
		g.removeNode(0);
		assertEquals(1,g.nodeSize());
		
		g.addNode(2);
		g.addNode(2);
		g.addNode(3);
		assertEquals(3,g.nodeSize());
	}

	@Test
	public void testEdgeSize() {
		weighted_graph g=new WGraph_DS();
		assertEquals(0,g.edgeSize());
		
		g.addNode(0);
		g.addNode(1);
		
		g.connect(0, 1, 3.14);
		assertEquals(1,g.edgeSize());
		
		g.addNode(2);
		g.addNode(3);
		g.connect(2, 1, 14);
		assertEquals(2,g.edgeSize());
		g.removeEdge(0, 1);
		assertEquals(1,g.edgeSize());
		
	}

	@Test
	public void testGetMC() {
		weighted_graph g=new WGraph_DS();
		assertEquals(0,g.getMC());
		g.addNode(1);
		assertEquals(1,g.getMC());
		g.addNode(2);
		g.addNode(3);
		 g.connect(1,2,584.2);
		assertEquals(4,g.getMC());
		g.removeEdge(1,2);
		assertEquals(5,g.getMC());
    
	}

}