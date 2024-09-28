/*
 * 
 * This class holds data for a GraphEdge and has getters and setters.
 */
public class GraphEdge {
	
	//instance variables for the nodes, edge type, and edge label
	private GraphNode firstEP;
	private GraphNode secondEP;
	private int type;
	private String label;
	
	//constructor that sets everything
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.firstEP = u;
		this.secondEP = v;
		this.type = type;
		this.label = label;
	}
	
	//returns first end point graph node
	public GraphNode firstEndpoint() {
		return this.firstEP;
	}
	
	//returns second end point graph node
	public GraphNode secondEndpoint() {
		return this.secondEP;
	}
	
	//returns the type of edge (num of coins needed if it's a door)
	public int getType() {
		return this.type;
	}
	
	//sets the type of an edge
	public void setype(int type) {
		this.type = type;
	}
	
	//returns the label of the edge
	public String getLabel() {
		return this.label;
	}
	
	//sets the label of the edge
	public void setLabel(String label) {
		this.label = label;
	}
	
}
