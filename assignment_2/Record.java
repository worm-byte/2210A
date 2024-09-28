/*

This class creates a Record that holds a Key and data.
 */
public class Record {
	//instance variables for the key and data
	private final Key theKey;
	private final String data;

	//constructor to set the key and data instance variables
	public Record(Key k, String theData) {
		this.theKey = k;
		this.data = theData;
	}

	//returns the key
	public Key getKey() {
		return this.theKey;
	}

	//returns the data
	public String getDataItem() {
		return this.data;
	}

}
