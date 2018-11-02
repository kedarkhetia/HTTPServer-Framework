package cs601.project3.invertedindex;

/**
 * Tuple class is used to store the frequency of text
 * appearing in an object. It also implements comparable
 * interface for sorting objects of type tuple.
 * 
 * @author kmkhetia
 *
 */
public class Tuple implements Comparable<Tuple>{
	private AmazonDataStructure object;
	private int frequency;
	
	/**
	 * Sets the object value.
	 * 
	 * @param object
	 * @return void
	 */
	public void setObject(AmazonDataStructure object) {
		this.object = object;
	}
	
	/**
	 * gets the object value.
	 * 
	 * @return void
	 */
	public AmazonDataStructure getObject() {
		return this.object;
	}
	
	/**
	 * Sets the frequency value.
	 * 
	 * @param frequency
	 * @return void
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * It compares two tuple values.
	 * 
	 * @param tuple
	 * @return int
	 */
	@Override
	public int compareTo(Tuple tuple) {
		return tuple.frequency - frequency;
	}
	
	/**
	 * The function returns the string representation of an object.
	 * 
	 * @return String
	 */
	@Override 
	public String toString(){
		return object.toString();
	}
	
}
