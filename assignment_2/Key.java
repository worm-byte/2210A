/*

This class creates a Key that holds a label and type.
 */
public class Key {

	//instance variables for label and type
	private String label;
	private int type;

	/*
	constructor that sets the label and type
	converts label to lowercase first
	 */
	public Key(String theLabel, int theType) {
		String l = theLabel.toLowerCase();
		this.label = l;
		this.type = theType;
	}

	//returns label
	public String getLabel() {
		return this.label;
	}

	//returns type
	public int getType() {
		return this.type;
	}

	/*
	compares this key to another to see where it would place
	lexicographically. if this.label == other.label and
	this.type == other.type it will return 0. if this label is less
	than other label or the labels have same value lexicographically but
	this.type is smaller than other.type, then it returns -1. if the
	other key is smaller, it will return 1.
	 */
	public int compareTo(Key k) {
	    int result = this.label.compareTo(k.getLabel());

	    if (result == 0 && this.type == k.getType()) {
	        return 0;
	    } else if (result < 0 || (result == 0 && this.type < k.getType())) {
	        return -1;
	    } else {
	        return 1;
	    }
	}
	
}


