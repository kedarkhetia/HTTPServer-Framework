package cs601.project3.defaulthandler;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class PageNotFoundHandler implements Handler{
	
	@Override
	public HTTPResponse handle(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), "error", 404);
		response.setResponse("<html> " + 
				"<head><title>TEST</title></head>"
				+ "<body>"
				+ "Page Not Found!" +
				"</body>" + 
				"</html>");
		return response;
	}
}
