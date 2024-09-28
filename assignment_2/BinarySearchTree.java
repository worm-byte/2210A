/*

This class has methods to create a Binary Search Tree
using BSTNodes.
 */
public class BinarySearchTree {

    //instance variable root of the tree
    private BSTNode root;

    //constructor creates an empty tree
    public BinarySearchTree() {
        root = new BSTNode(null);
    }

    //returns root of tree
    public BSTNode getRoot() {
        return this.root;
    }

    /*searches for a node storing key k when given the root of a tree
    if it cannot find it, it will return null
     */
    public BSTNode get(BSTNode r, Key k) {
        if (r == null || r.getRecord() == null) return null;
        if (r.getRecord().getKey().compareTo(k) == 0) {
            return r;
        } else if (r.getRecord().getKey().compareTo(k) < 0) {
            return get(r.getRightChild(), k); //check right subtree
        } else {
            return get(r.getLeftChild(), k); //check left subtree
        }
    }

    /*
    inserts a new node into the tree and places it in lexicographical order
    based on which other nodes are already in the tree.
    throws a DictionaryException if the record is already in the tree.
     */
    public void insert(BSTNode r, Record d) throws DictionaryException {
        if (r == null || r.getRecord() == null) {
            // The tree is currently empty, so set root node
            root.setRecord(d);
            root.setLeftChild(new BSTNode(null));
            root.setRightChild(new BSTNode(null));
        } else {
            BSTNode current = r;
            //check what is the lexicographical value of key in record
            int compare = d.getKey().compareTo(current.getRecord().getKey());
            if (compare == 0) {
                throw new DictionaryException("A record with the given key (" + d.getKey().getLabel() + "," + d.getKey().getType() + ") is already in the dictionary");
            } else if (compare < 0) {
                if (current.getLeftChild().getRecord() == null) { //set new node as left child of r if r has no left child
                    current.setLeftChild(new BSTNode(d));
                    current.getLeftChild().setLeftChild(new BSTNode(null)); // make sure new node is an internal node by adding leaves as null children
                    current.getLeftChild().setRightChild(new BSTNode(null));
                } else {
                    insert(current.getLeftChild(), d); //if r does have a left child, search recursively for the next available spot to insert
                }
            } else {
                if (current.getRightChild().getRecord() == null) { //set new node as right child of r if r has no right child
                    current.setRightChild(new BSTNode(d));
                    current.getRightChild().setLeftChild(new BSTNode(null)); // make sure new node is an internal node by adding leaves as null children
                    current.getRightChild().setRightChild(new BSTNode(null));
                } else {
                    insert(current.getRightChild(), d); //if r does have a right child, search recursively for the next available spot to insert
                }
            }
        }
    }

    /*
    removes a node from the tree and changes the appropriate connections between parents and children.
    throws a DictionaryException if the tree doesn't store a node with the given key.
     */
    public void remove(BSTNode r, Key k) throws DictionaryException {
        BSTNode p = get(r, k); //first search for the given key and store in node p
        if (p == null || p.getRecord() == null) { //if p is null or record of p is null, it's not in the tree
            throw new DictionaryException("No record in the dictionary has key (" + k.getLabel() + "," + k.getType() + ")");
        } else {
            if (p.getLeftChild().getRecord() == null && p.getRightChild().getRecord() == null) {
                //node has no children
                if (p.getParent() == null) {
                    this.root.setRecord(null); // root node has no children
                } else if (p == p.getParent().getLeftChild()) { //if p is a left child of its parent, set parent left child to null
                    p.getParent().setLeftChild(new BSTNode(null));
                } else {
                    p.getParent().setRightChild(new BSTNode(null)); //if p is a right child of its parent, set parent right child to null
                }
            } else if (p.getLeftChild().getRecord() == null || p.getRightChild().getRecord() == null) {
                //node has one child
                BSTNode child = null;
                if(p.getLeftChild() != null){ //check if it's the left or right child and put in a variable
                    child = p.getLeftChild();
                }else{
                    child = p.getRightChild();
                }
                if (p.getParent() == null) {
                    this.root = child; // remove the root node with one child and set child to root
                    child.setParent(null);
                } else if (p == p.getParent().getLeftChild()) { //if p is the left child of it's parent, set p's child as the left child of parent
                    p.getParent().setLeftChild(child);
                } else {
                    p.getParent().setRightChild(child); //if p is the right child of it's parent, set p's child as the right child of parent
                }
                child.setParent(p.getParent()); //set the child's parent as p's parent
            } else {
                // node has two children
                BSTNode successor = successor(p,k); //find the smallest right child and set as successor
                p.setRecord(successor.getRecord());
                remove(successor, successor.getRecord().getKey());
            }
        }
    }

    //finds the smallest right child of given node and key
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode node = get(r, k); //first find the node
        if (node == null || node.getRecord() == null) {
        	return findSuccessor(r,k);
        }
        if (node.getRightChild().getRecord() != null) { //if r has a right child, find the smallest right child and return
            return smallest(node.getRightChild());
        }
        BSTNode parent = node.getParent(); //if r doesn't have a right child, find an ancestor to be the successor
        while (parent != null && node == parent.getRightChild()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    //finds the largest right child of a given node and key
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode node = get(r, k); //first find the node
        if (node == null || node.getRecord() == null) {
            // If key k is not found in the dictionary, find the largest node smaller than k
            //return null if there is no smaller node than k
            return findPredecessor(r, k);
        }
        if (node.getLeftChild().getRecord() != null) { //if r has a left child, find the largest left child and return
            return largest(node.getLeftChild());
        }
        BSTNode parent = node.getParent(); //if r doesn't have a right child, find an ancestor and return
        while (parent != null && node == parent.getLeftChild()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }


    //finds the smallest node in the subtree of r
    public BSTNode smallest(BSTNode r) {
        if (r == null || r.getRecord() == null) return null; //if r is null or its record, return null
        BSTNode node = r;
        while (node.getLeftChild().getRecord() != null) { //while there is a left child, keep going left
            node = node.getLeftChild();
        }
        return node; //this will return r if there are no left children in tree
    }

    //finds the largest node in the subtree of r
    public BSTNode largest(BSTNode r) {
        if (r == null || r.getRecord() == null) return null; //if r is null or its record null, return null
        BSTNode node = r;
        while (node.getRightChild().getRecord() != null) { //while there is a right child, keep going right
            node = node.getRightChild();
        }
        return node; //this will return r if there is no right children in tree
    }

    /*
    private method that will find the node storing the largest key that is still less than k
     */
    private BSTNode findPredecessor(BSTNode r, Key k) {
        if (r == null || r.getRecord() == null) { //if r is null or its record is null, return null
            return null;
        }
        if (r.getRecord().getKey().compareTo(k) < 0) { //if value of r's key is less than k lexicographically
            BSTNode rightResult = findPredecessor(r.getRightChild(), k); //find the max right child that's still less than k
            if (rightResult != null) {
                return rightResult; //if one exists return right child
            }
            else{
                return r;
            }
        } else {
            return findPredecessor(r.getLeftChild(), k); //check left subtree if no right child exists that has key less than k
        }
    }
    
    private BSTNode findSuccessor(BSTNode r, Key k) {
        
        
        if (r == null || r.getRecord() == null) return null;
        
        if(r.getRecord().getKey().compareTo(k) > 0) {
        	BSTNode leftResult = findSuccessor(r.getLeftChild(),k);
        	if(leftResult != null) {
            	return leftResult;
            }else {
            	return r;
            }
        }
       
        else {
        	return findSuccessor(r.getRightChild(),k);
        }
    }

}
