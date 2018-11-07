package cs601.project3.defaulthandler;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConstants;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

/**
 * This is a handler class for situation when
 * requested path is not configured by Application.
 * 
 * @author kmkhetia
 *
 */ 
public class PageNotFoundHandler implements Handler{
	
	@Override
	public HTTPResponse handle(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), HTTPConstants.STATUS_PNF, HTTPConstants.STATUS_CODE_PNF);
		response.setResponse("<html> " + 
				"<head><title>TEST</title></head>"
				+ "<body>"
				+ "Page Not Found!" +
				"</body>" + 
				"</html>");
		return response;
	}
}
