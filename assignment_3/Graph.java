/*
 * Rosaline Scully
 * August 11, 2024
 * Student ID: 250966670
 * 
 * This class implements the GraphADT and has multiple methods that may be needed
 * to create a graph and check what edges and nodes are in it.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Graph implements GraphADT {
	
	//instance variables which is an adjacencyList and a list of graphNodes
	private Map<Integer,List<GraphEdge>> adjacencyList;
	private List<GraphNode> graphNodes;
	
	/*
	 * constructor that initiates a hashtable to store the adjacencyList
	 * and creates a list of graphNodes
	 */
	public Graph(int n) {
		adjacencyList = new HashMap<>();
		graphNodes = new ArrayList<>(n);
		
		//make empty lists for each spot in the hash table and add nodes to graphNodes list
		for(int i = 0; i < n; i++) {
			GraphNode node = new GraphNode(i);
			graphNodes.add(node);
			adjacencyList.put(i, new ArrayList<>());
		}
		
	}
	
	//inserts an edge in the adjacencyList if the nodes exist and it's not already on the list
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		//first check if the node is in the map
	    if (!inGraph(nodeu,nodev)) {
	        throw new GraphException("This node is not in the map.");
	    }

	    //check if an edge is already in the adjacencyList
	    if (edgeExists(nodeu, nodev)) {
	        throw new GraphException("This edge is already in the map");
	    } else { //add edge to both lists if it's not already in it
	        GraphEdge newEdge = new GraphEdge(nodeu, nodev, type, label);
	        adjacencyList.get(nodeu.getName()).add(newEdge);
	        adjacencyList.get(nodev.getName()).add(newEdge);
	    }
	}


	//returns a node from list of nodes on map if it exists
	@Override
	public GraphNode getNode(int u) throws GraphException {
		if(u < 0 || u >= graphNodes.size()) {
			throw new GraphException("This node is not in the map.");
		}
		
		return graphNodes.get(u);
		
	}

	//returns an iterator of all the edges incident of a given node if it exists
	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		
		//first check if the node is on the graph
		if(u.getName() < 0 || u.getName() >= graphNodes.size()) {
			throw new GraphException("This node is not in the map");
		}
		
		//make an iterator of what's in the adjacencyList at the node
		Iterator<GraphEdge> iterator = adjacencyList.get(u.getName()).iterator();
		
		//return the iterator if it's not empty or null if it is empty
		if(iterator.hasNext()) {
			return iterator;
		}else {
			return null;
		}
	}

	//get an edge between two nodes if it exists
	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		
		//first check if nodes are in the graph
		if(!inGraph(u,v)) {
			throw new GraphException("This node is not in the map");
		}
		
		//get the edge lists for both nodes
		List<GraphEdge> edgesU = adjacencyList.get(u.getName());
		List<GraphEdge> edgesV = adjacencyList.get(v.getName());
		List<GraphEdge> shorterList;
		
		//find the shorter list
		if(edgesU.size() > edgesV.size()) {
			shorterList = edgesV;
		}else {
			shorterList = edgesU;
		}
		
		//iterate through the edges of the shorter list and see if any connects nodes u & v
		for(GraphEdge edge:shorterList) {
			if(edge.firstEndpoint().equals(u) && edge.secondEndpoint().equals(v) ||
					edge.firstEndpoint().equals(v) && edge.secondEndpoint().equals(u)) {
				return edge;
			}
		}
		
		//if no edge is found return null
		return null;
		
	}

	//see if two nodes are adjacent to each other. returns true or false.
	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		
		//first check if both nodes are in the graph
		if(!inGraph(u,v)) {
			throw new GraphException("This node is not in the map");
		}
		
		//then check if an edge exists between them
		if(getEdge(u,v) == null) {
			return false;
		}else{
			return true;
		}
		
		
	}
	//private method that checks if two nodes are in the graph
	private boolean inGraph(GraphNode nodeu, GraphNode nodev) {
		 if (!adjacencyList.containsKey(nodeu.getName()) || !adjacencyList.containsKey(nodev.getName())) {
		        return false;
		    }
		 
		 else {
			 return true;
		 }
	}
	//check if an edge exists between two given nodes
	private boolean edgeExists(GraphNode nodeu, GraphNode nodev) {
	    // Check the adjacency list for nodeu
	    List<GraphEdge> edgesU = adjacencyList.get(nodeu.getName());
	    for (GraphEdge edge : edgesU) {
	        if ((edge.firstEndpoint().equals(nodeu) && edge.secondEndpoint().equals(nodev)) ||
	            (edge.firstEndpoint().equals(nodev) && edge.secondEndpoint().equals(nodeu))) {
	            return true;
	        }
	    }

	    // Check the adjacency list for nodev
	    List<GraphEdge> edgesV = adjacencyList.get(nodev.getName());
	    for (GraphEdge edge : edgesV) {
	        if ((edge.firstEndpoint().equals(nodeu) && edge.secondEndpoint().equals(nodev)) ||
	            (edge.firstEndpoint().equals(nodev) && edge.secondEndpoint().equals(nodeu))) {
	            return true;
	        }
	    }

	    return false;
	}

}
