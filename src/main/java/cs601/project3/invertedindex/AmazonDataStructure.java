package cs601.project3.invertedindex;

/**
 * AmazonDataStructure is an abstract class that acts as a parent class
 * for Review and QuestionAnswer classes.
 * 
 * @author kmkhetia
 */
public abstract class AmazonDataStructure {
	String asin;

	/**
	 * Gets the asin value.
	 * 
	 * @return String
	 */
	public String getAsin(){
		return asin;
	}
	
	/**
	 * Sets the asin value.
	 * 
	 * @param asin
	 * @return void
	 */
	public void setAsin(String asin) {
		this.asin = asin;
	}
	
	/**
	 * It will return the String to store to inverted index.
	 * 
	 * @return String
	 */
	public abstract String getText();
	
	/**
	 * It will return the HTML compatible text
	 * 
	 * @return String
	 */
	public abstract String getHTMLText();
}
