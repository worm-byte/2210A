/*
Rosaline Scully
August 3, 2024
Student ID 250966670

This class is a data structure for a Binary Search Tree node
with getters and setters.
 */
public class BSTNode {

	//instance variables needed for each node
	private Record r;
	private BSTNode leftChild;
	private BSTNode rightChild;
	private BSTNode parent;

	/*
	constructor that creates a node, sets the record and sets children and parent to null
	 */
	public BSTNode(Record item) {
		this.r = item;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
	}

	//returns record
	public Record getRecord() {
		return this.r;
	}

	//sets record
	public void setRecord(Record d) {
		this.r = d;
	}

	//returns left child
	public BSTNode getLeftChild() {
		return this.leftChild;
	}

	//returns right child
	public BSTNode getRightChild() {
		return this.rightChild;
	}

	//returns parent
	public BSTNode getParent() {
		return this.parent;
	}

	//sets left child and makes this node the parent of the left child if not null
	public void setLeftChild(BSTNode u) {
		this.leftChild = u;
		if(u != null) {
			u.setParent(this);
		}
	}

	//sets right child and makes this node the parent of the right child if not null
	public void setRightChild(BSTNode u) {
		this.rightChild = u;
		if(u != null) {
			u.setParent(this);
		}
	}

	//sets parent
	public void setParent(BSTNode u) {
		this.parent = u;
	}

	/*
	determines if this node is a leaf. a node is considered a leaf if
	both children are null and the node itself is null since only
	internal nodes will be storing information.
	 */
	public boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null && this.r == null;
	}
}
