package cs601.project3.invertedindex;

import java.util.List;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConstants;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;


public class FindHandler implements Handler {
	private InvertedIndex invertedIndex;
	
	public FindHandler(InvertedIndex invertedIndex) {
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
		response.setResponseHeader(request.getProtocol(), HTTPConstants.STATUS_OK, HTTPConstants.STATUS_CODE_OK);
		String asin = null;
		if(request.getParams() != null) {
			asin = request.getParams().get("asin");
		}
		List<AmazonDataStructure> datalist = invertedIndex.find(asin);
		response.setResponse(getPostResponseString(datalist));
		return response;
	}
	
	public String getPostResponseString(List<AmazonDataStructure> datalist) {
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
				+ "\n\t\t\t\t<th>ReviewerId/QuestionType</th>"
				+ "\n\t\t\t\t<th>ReviewText/Question</th>"
				+ "\n\t\t\t\t<th>Overall/Answer</th>"
				+ "\n\t\t\t</tr>";
		if(datalist != null) {
			for(AmazonDataStructure data : datalist) {
				responseString += data.getHTMLText();
			}
		}
		responseString += "\n\t\t</table>"
				+ "\n\t</body>"
				+ "</html>";
		return responseString;
	}

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
				+ "document.getElementById(\'findasin\').action = \"/find?asin=\" + document.getElementById(\'asin\').value;"
				+ "}</script>"
				+ "\n\t</head>"
				+ "\n\t<body>"
				+ "\n\t\t<h2>Find ASIN</h2>"
				+ "\n\t\t<form method=\"POST\" id=\"findasin\" onsubmit=\"getUrl()\">"
				+ "\n\t\t\tASIN:<br/>"
				+ "\n\t\t\t<input type=\"text\" id=\"asin\" name=\"asin\" />"
				+ "\n\t\t\t<br/>"
				+ "\n\t\t</form>"
				+ "\n\t\t\t<button type=\"submit\" form=\"findasin\" value=\"Submit\">Submit</button>"
				+ "\n\t</body>"
				+ "\n</html>";
	}
}
