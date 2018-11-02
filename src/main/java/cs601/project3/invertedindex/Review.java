package cs601.project3.invertedindex;

/**
 * A class to represent each Review as structure of 
 * asin, reviewerId, reviewText and OverallScore.
 * 
 * @author kmkhetia
 */
public class Review extends AmazonDataStructure{
	private String reviewerID;
	private String reviewText;
	private double overall;
	
	Review(String reviewerID, String asin, String reviewText, double overall) {
		this.reviewerID = reviewerID;
		this.asin = asin;
		this.reviewText = reviewText;
		this.overall = overall;
	}
	
	/**
	 * Gets reviewerID value.
	 * 
	 * @return String
	 */
	public String getReviewerID() {
		return reviewerID;
	}
	
	/**
	 * Sets reviewerID value.
	 * 
	 * @param reviewerID
	 * @return void
	 */
	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID;
	}
	
	/**
	 * Gets reviewText value.
	 * 
	 * @return String
	 */
	public String getReviewText(){
		return reviewText;
	}
	
	/**
	 * Sets reviewText value.
	 * 
	 * @param reviewText
	 * @return void
	 */
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	/**
	 * Gets overAllScore value.
	 * 
	 * @return double
	 */
	public double getOverall(){
		return overall;
	}
	
	/**
	 * Sets overAllScore value.
	 * 
	 * @param overall
	 * @return void
	 */
	public void setOverall(double overall) {
		this.overall = overall;
	}
	
	/**
	 * Returns the string representation of Review Object.
	 * 
	 * @return String
	 */
	@Override
	public String toString(){
		return "ASIN: " + this.asin + "\nReviewId: " + this.reviewerID + "\nReviewText: " + this.reviewText + "\nOverall: " + this.overall + "\n";
	}
	
	/**
	 * It will return the String to store to inverted index.
	 * 
	 * @return String
	 */
	@Override
	public String getText() {
		return reviewText;
	}

	/**
	 * It will return the HTML compatible text
	 * 
	 * @return String
	 */
	@Override
	public String getHTMLText() {
		return "\n\t\t\t<tr>"
				+ "\n\t\t\t\t<td>"+this.getAsin()+"</td>"
				+ "\n\t\t\t\t<td>"+this.getReviewerID()+"</td>"
				+ "\n\t\t\t\t<td>"+this.getReviewText()+"</td>"
				+ "\n\t\t\t\t<td>"+this.getOverall()+"</td>"
				+ "\n\t\t\t</tr>";
	}
}
