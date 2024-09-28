/*
 * 
 * This class creates a maze by reading a file and finds a solution (if it exists)
 * for the maze. 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;



public class Maze {

	/*
	 * instance variables graph to hold the graph, starting and ending nodes, 
	 * number of coins to start with, and a variable to hold the path.
	 */
	private Graph graph;
	private int start;
	private int end;
	private int coins;
	private Stack<GraphNode> path;
	

	//constructor to initialize the maze with an input file
	public Maze(String inputFile) throws MazeException {

		//create a buffer reader and call a private method to read input
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			readInput(reader);
		} catch (IOException | GraphException e) {
			e.printStackTrace();
			throw new MazeException("Error reading file.");
		} 
		
            
	}

	//return this graph if it exists
	public Graph getGraph() throws MazeException{
		
		if(this.graph == null) throw new MazeException("The maze is empty.");
		
		return this.graph;
	}

	//returns an iterator of a solution if it exists, otherwise it returns null
	public Iterator<GraphNode> solve() throws MazeException, GraphException{
		
		path = new Stack<>();

        return DFS(coins,getGraph().getNode(start));
	}

	/*
	 * private method that does a depth first search to try and find a solution.
	 * it reduces the number of coins as it goes along using them.
	 * recursively searches for a path by putting neighbouring nodes back into the DFS algorithm.
	 * returns an iterator of the path if found, or null if not found.
	 */
	private Iterator<GraphNode> DFS(int k, GraphNode go) throws GraphException, MazeException {
		
		//mark current node visited
		go.mark(true);
		
		//add current node to path
		path.push(go);
		
		//base case: if end node has been found, return the path as iterator
		if(go.getName() == end) {
			return path.iterator();
		}
		
		//get all incident edges of current node
		Iterator<GraphEdge> edgesV = getGraph().incidentEdges(go);

		//if current node has no edges back track
		if(edgesV == null) {
			path.pop();
			go.mark(false);
			return null;
		}
		
		//go through each edge
		while(edgesV.hasNext()) {
			
			GraphEdge edge = edgesV.next();
			
			//skip walls
			if(edge.getLabel() == "wall") continue;
			
			GraphNode firstEndPoint = edge.firstEndpoint();
			GraphNode secondEndPoint = edge.secondEndpoint();
			GraphNode neighbour;
			
			//determine who is a neighbouring node
			if(firstEndPoint.getName() == go.getName()) {
				neighbour = secondEndPoint;
			}else {
				neighbour = firstEndPoint;
			}
			
			//if neighbour hasn't been visited yet
			if(!neighbour.isMarked()) {
				//calculate remaining coins after visiting edge
				int newCoins = visit(edge,k);
				
				//if not enough coins to visit next node, skip it
				if(newCoins < 0) {
					continue;
				}
				
				//do DFS on neighbouring node
				Iterator<GraphNode> resultNeighbour = DFS(newCoins,neighbour);
				
				//if a valid path is found, return it
				if(resultNeighbour != null) {
					return resultNeighbour;
				}
			}
			
		}
		
		//backtrack and unmark 
		path.pop();
		go.mark(false);
		
		//if no path is found return null
		return null;
		
		
	}
	
	//private method that updates coins if edge is a door
	private int visit(GraphEdge edge, int coins) {
		if(edge.getLabel() == "door") {
			if(edge.getType() == 0) return coins;
			else {
				int newCoins = coins - edge.getType();
				if(newCoins >= 0) {
					edge.setype(0);
					return newCoins;
				}else {
					return newCoins;
				}
				
			}
		}
		
		return coins;
	}

	/*
	 * private method that reads the input of a file and creates a maze by adding edges between the nodes of the graph.
	 * throws exceptions if there are any errors reading the file.
	 */
	private void readInput(BufferedReader inputReader) throws IOException, GraphException {
 
		try {
			String line;
			line = inputReader.readLine();//ignore the first line
			
			//initialize maze with these numbers
			int width = Integer.parseInt(inputReader.readLine().trim());
			int length = Integer.parseInt(inputReader.readLine().trim());
			int numCoins = Integer.parseInt(inputReader.readLine().trim());
			
			//initialize size of graph and coins 
			graph = new Graph(width * length);
			coins = numCoins;
			
			//n keeps track what node currently on
			int n = 0;
			
			while((line = inputReader.readLine()) != null) {
				
				
				for(int i = 0; i < width*2-2; i++) {
					
					//read character by character and insert edges with appropriate label
					char currentChar = line.charAt(i);
					char nextChar = line.charAt(i+1);
					
					//initialize start and end if you find it
					if(currentChar == 's') {
						this.start = n;
					}else if(currentChar == 'x') {
						this.end = n;
					}

					if(Character.isDigit(currentChar) && nextChar == 'o' || nextChar == 's' || nextChar == 'x') {
						insertEdge(n,n+1,Character.getNumericValue(currentChar),"door");
						n++;
					}else if(currentChar == 'c' && nextChar == 'o' || nextChar == 's' || nextChar == 'x') {
						insertEdge(n,n+1,0,"corridor");
						n++;
					}else if(currentChar == 'w'&& nextChar == 'o' || nextChar == 's' || nextChar == 'x') {
						insertEdge(n,n+1,0,"wall");
						n++;
					}
					

					
				}
				
				line = inputReader.readLine();
				n++;
				if(line != null) {
					//initialize edges to nodes below the row above
					for(int i = 0; i < width*2-1; i++) {
						if(i%2 == 1) continue; //i don't need to connect edges between edges, i am connecting edges between nodes only
						
						char c = line.charAt(i);
						
						//read character by character and insert appropriate edge with label
						if(Character.isDigit(c)) {
							insertEdge(n,n-width,Character.getNumericValue(c),"door");
						}else if(c == 'c') {
							insertEdge(n,n-width,0,"corridor");
						}else if(c == 'w') {
							insertEdge(n,n-width,0,"wall");
						}
						n++;
					}
					n = n - width;
				}else {
					return;
				}
			}
		}catch (GraphException | NumberFormatException e){
			throw new GraphException("Error with graph");
		}catch (IOException e) {
			throw new IOException();
		}
		 
	}

	//private method to insert and edge in the graph between two nodes
	private void insertEdge(int node1, int node2, int linkType, String label) throws GraphException {
		
		GraphNode nodeOne = graph.getNode(node1);
		GraphNode nodeTwo = graph.getNode(node2);
		
		graph.insertEdge(nodeOne, nodeTwo, linkType, label);
		
		
	}

}
