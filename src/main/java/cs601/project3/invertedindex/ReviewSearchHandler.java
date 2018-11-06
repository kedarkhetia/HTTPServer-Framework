package cs601.project3.invertedindex;

import java.util.LinkedList;
import java.util.List;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class ReviewSearchHandler implements Handler{

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

	public synchronized HTTPResponse post(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), "OK", 200);
		String[] searchText = null;
		List<Tuple> reviews = new LinkedList<Tuple>();
		if(!request.getParams().isEmpty()) {
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
		response.setResponse(getPostResponseString(reviews));
		return response;
	}
	
	public String getPostResponseString(List<Tuple> datalist) {
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
				responseString += data.getObject().getHTMLText();
			}
		}
		responseString += "\n\t\t</table>"
				+ "\n\t</body>"
				+ "<html>";
		return responseString;
	}

	public synchronized HTTPResponse get(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), "OK", 200);
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
				+ "\n\t\t\tSearch Text:<br>"
				+ "\n\t\t\t<input type=\"text\" id=\"text\" name=\"text\">"
				+ "\n\t\t\t<br>"
				+ "\n\t\t</form>"
				+ "\n\t\t\t<button type=\"submit\" form=\"reviewsearch\" value=\"Submit\">Submit</button>"
				+ "\n\t</body>"
				+ "\n</html>";
	}

}
