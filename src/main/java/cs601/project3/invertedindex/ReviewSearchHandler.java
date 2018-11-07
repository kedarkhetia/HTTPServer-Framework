package cs601.project3.invertedindex;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConstants;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

/**
 * 
 * This class belongs to SearchApplication. It exposes
 * search API for invertedIndex.
 * 
 * @author kmkhetia
 *
 */
public class ReviewSearchHandler implements Handler{
	private final static Logger log = LogManager.getLogger(FindHandler.class);

	private InvertedIndex invertedIndex;
	
	public ReviewSearchHandler(InvertedIndex invertedIndex) {
		this.invertedIndex = invertedIndex;
	}
	
	@Override
	public synchronized HTTPResponse handle(HTTPRequest request) {
		if(request.getMethod().equals("GET")) {
			return get(request);
		}
		else {
			return post(request);
		}
	}
	
	/**
	 * This method is called when the post request
	 * comes to the server for ReviewSearch API.
	 * 
	 * @param request
	 * @return response
	 */
	public synchronized HTTPResponse post(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), HTTPConstants.STATUS_OK, HTTPConstants.STATUS_CODE_OK);
		String[] searchText = null;
		List<Tuple> reviews = new LinkedList<Tuple>();
		if(!request.getParams().isEmpty()) {
			if(request.getParams().get("query") != null) {
				searchText = request.getParams().get("query").split("\\s+");
				for(String queryText : searchText){
					if(queryText != null && !queryText.isEmpty()) {
						List<Tuple> result = invertedIndex.search(queryText);
						if(result != null) {
							reviews.addAll(result);
						}
					}
				}
			}
		}
		response.setResponse(getPostResponseString(reviews));
		return response;
	}
	
	public String getPostResponseString(List<Tuple> datalist) {
		int count = 0;
		String responseString = "<html>"
				+ "\n\t<head>"
				+ "\n\t\t<style>"
				+ "\n\t\t\t table, th, td {"
				+ "border: 1px solid black;"
				+ "}"
				+ "\n\t\t</style>"
				+ "\n\t</head>"
				+ "\n\t<body>"
				+ "\n\t\t<table style=\"width:100%; border:1px solid black;\">"
				+ "\n\t\t\t<tr>"
				+ "\n\t\t\t\t<th>ASIN</th>"
				+ "\n\t\t\t\t<th>ReviewerId</th>"
				+ "\n\t\t\t\t<th>ReviewText</th>"
				+ "\n\t\t\t\t<th>Overall</th>"
				+ "\n\t\t\t</tr>";
		if(datalist != null) {
			for(Tuple data : datalist) {
				if(count > 50) {
					break;
				}
				responseString += data.getObject().getHTMLText();
				log.info("Sending HTML for, " + data.getObject().getText());
				count++;
			}
		}
		responseString += "\n\t\t</table>"
				+ "\n\t</body>"
				+ "</html>";
		return responseString;
	}
	
	/**
	 * This method is called when the get request
	 * comes to the server ReviewSearch API.
	 * 
	 * @param request
	 * @return response
	 */
	public synchronized HTTPResponse get(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), HTTPConstants.STATUS_OK, HTTPConstants.STATUS_CODE_OK);
		response.setResponse(getGetResponseString());
		return response;
	}
	
	public String getGetResponseString() {
		return "<html>"
				+ "\n\t<head><script>"
				+ "function getUrl() {"
				+ "document.getElementById(\'reviewsearch\').action = \"/reviewsearch?query=\" + document.getElementById(\'text\').value;"
				+ "}</script>"
				+ "\n\t</head>"
				+ "\n\t<body>"
				+ "\n\t\t<h2>Review Search</h2>"
				+ "\n\t\t<form method=\"POST\" id=\"reviewsearch\" onsubmit=\"getUrl()\">"
				+ "\n\t\t\tSearch Text:<br/>"
				+ "\n\t\t\t<input type=\"text\" id=\"text\" name=\"text\" />"
				+ "\n\t\t\t<br/>"
				+ "\n\t\t</form>"
				+ "\n\t\t\t<button type=\"submit\" form=\"reviewsearch\" value=\"Submit\">Submit</button>"
				+ "\n\t</body>"
				+ "\n</html>";
	}

}
