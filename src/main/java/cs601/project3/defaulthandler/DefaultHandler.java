package cs601.project3.defaulthandler;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class DefaultHandler implements Handler {

	@Override
	public HTTPResponse handle(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setProtocol(request.getProtocol());
		response.setStatusCode(200);
		response.setStatus("OK");
		response.setResponse("<html> " + 
				"<head><title>TEST</title></head>" + 
				"<body>This is a default " + request.getMethod() + " Method!"+
				"</body>" + 
				"</html>");
		return response;
	}

}
