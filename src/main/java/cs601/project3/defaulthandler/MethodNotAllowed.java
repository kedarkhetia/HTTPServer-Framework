package cs601.project3.defaulthandler;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConstants;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class MethodNotAllowed implements Handler{
	@Override
	public HTTPResponse handle(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), HTTPConstants.STATUS_MNA, HTTPConstants.STATUS_CODE_MNA);
		response.setResponse("<html>\n" + 
				"\t<head>\n\t\t<title>\n\t\t\tTEST\n\t\t</title>\n\t</head>\n" + 
				"\t<body>\n\t\tMethod Not Allowed!\n\t</body>\n" + 
				"</html>");
		return response;
	}
}
