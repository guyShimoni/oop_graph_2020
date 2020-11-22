# oop_graph_2020

EX1_OOP

About:

This project represent a weighted undirected graph with graph theory algoritms,
consists of 3 interfaces and 2 implementations.
The Data structor in this project are based on JAVA HashMap

Project Classes:

WGraph_Algo- This class represents the set of graph-theory algorithms, 
             implement undirected (positive) Weighted Graph Theory algorithms
             in this class i use Dijkstra's algorithm to provide the shortest path and distance
             from one node to another, considering the weights of each edge. 

WGraph_DS- WGraph_DS class is an implementation of weighted_graph interface. 
           The class contains an inner class - NodeInfo which is the implementation of a node_info
           interface. in this class the HashMap contains all the nodes in the grap.