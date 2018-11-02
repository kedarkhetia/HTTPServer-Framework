package cs601.project3.invertedindex;

/**
 * A class to represent each question answer as structure of 
 * asin, questionType, question and answer.
 * 
 * @author kmkhetia
 */
public class QuestionAnswer extends AmazonDataStructure{
	private String questionType;
	private String question;
	private String answer;
	
	QuestionAnswer(String questionType, String asin, String question, String answer) {
		this.questionType = questionType;
		this.asin = asin;
		this.question = question;
		this.answer = answer;
	}
	
	/**
	 * Gets questionType value.
	 * 
	 * @return String
	 */
	public String getQuestionType(){
		return questionType;
	}
	
	/**
	 * Sets questionType value.
	 * 
	 * @param questionType
	 * @return void
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	/**
	 * Gets question value.
	 * 
	 * @return String
	 */
	public String getQuestion(){
		return question;
	}
	
	/**
	 * Sets question value.
	 * 
	 * @param question
	 * @return void
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * Gets answer value.
	 * 
	 * @return String
	 */
	public String getAnswer(){
		return answer;
	}
	
	/**
	 * Sets answer value.
	 * 
	 * @param answer
	 * @return void
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/**
	 * Returns the string representation of QuestionAnswer Object.
	 * 
	 * @return String
	 */
	@Override
	public String toString(){
		return "ASIN: " + this.asin + "\nQuestion: " + this.question + "\nAnswer: " + this.answer + "\n";
	}
	
	/**
	 * It will return the String to store to inverted index.
	 * 
	 * @return String
	 */
	@Override
	public String getText() {
		return question + " " + answer;
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
				+ "\n\t\t\t\t<td>"+this.getQuestionType()+"</td>"
				+ "\n\t\t\t\t<td>"+this.getQuestion()+"</td>"
				+ "\n\t\t\t\t<td>"+this.getAnswer()+"</td>"
				+ "\n\t\t\t</tr>";
	}
}
