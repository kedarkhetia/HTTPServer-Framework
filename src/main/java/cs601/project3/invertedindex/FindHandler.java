package cs601.project3.invertedindex;

import java.util.List;

import cs601.project3.handler.Handler;
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

	private synchronized HTTPResponse post(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setProtocol("HTTP/1.1");
		response.setStatus("OK");
		response.setStatusCode(200);
		String asin = request.getParams().get("asin");
		List<AmazonDataStructure> datalist = invertedIndex.find(asin);
		response.setResponse(getPostResponseString(datalist));
		return response;
	}
	
	private String getPostResponseString(List<AmazonDataStructure> datalist) {
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
				+ "<html>";
		return responseString;
	}

	private synchronized HTTPResponse get(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setProtocol("HTTP/1.1");
		response.setStatus("OK");
		response.setStatusCode(200);
		response.setResponse(getGetResponseString());
		return response;
	}
	
	private String getGetResponseString() {
		return "<html>"
				+ "\n\t<head><script>"
				+ "function getUrl() {"
				+ "document.getElementById(\'findasin\').action = \"/find?asin=\" + document.getElementById(\'asin\').value;"
				+ "}</script>"
				+ "\n\t</head>"
				+ "\n\t<body>"
				+ "\n\t\t<h2>Find ASIN</h2>"
				+ "\n\t\t<form method=\"POST\" id=\"findasin\" onsubmit=\"getUrl()\">"
				+ "\n\t\t\tASIN:<br>"
				+ "\n\t\t\t<input type=\"text\" id=\"asin\" name=\"asin\">"
				+ "\n\t\t\t<br>"
				+ "\n\t\t</form>"
				+ "\n\t\t\t<button type=\"submit\" form=\"findasin\" value=\"Submit\">Submit</button>"
				+ "\n\t</body>"
				+ "\n</html>";
	}
}
