/*
 * Rosaline Scully
 * August 11, 2024
 * Student ID: 250966670
 * 
 * This class holds information about a GraphNode with getters and setters.
 */
public class GraphNode {

	//instance variables for name of node and if it's marked
    private int name;
    private boolean mark;
	
    //constructor to initialize node
	public GraphNode(int name) {
		this.name = name;
		this.mark = false;
	}

	//change the mark of the node
	public void mark(boolean mark) {
		this.mark = mark;
	}
	
	//check if a node is marked or not
	public boolean isMarked() {
		return this.mark;
	}
	
	//return the name(int) of the node
	public int getName() {
		return this.name;
	}
	
}
