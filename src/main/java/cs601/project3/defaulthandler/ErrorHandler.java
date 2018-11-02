package cs601.project3.defaulthandler;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class ErrorHandler implements Handler {

	@Override
	public HTTPResponse handle(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setProtocol(request.getProtocol());
		response.setStatusCode(405);
		response.setStatus("Method Not Allowed");
		response.setResponse("<html>\n" + 
				"\t<head>\n\t\t<title>\n\t\t\tTEST\n\t\t</title>\n\t</head>\n" + 
				"\t<body>\n\t\tMethod Not Allowed!\n\t</body>\n" + 
				"</html>");
		return response;
	}

}
