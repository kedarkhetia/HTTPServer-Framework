package cs601.project3.slack;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class ChatHandler implements Handler{
	SlackClient client;
	
	public ChatHandler(SlackClient client) {
		this.client = client;
	}

	@Override
	public HTTPResponse handle(HTTPRequest request) {
		if(request.getMethod().equals("GET")) {
			return get(request);
		}
		else {
			return post(request);
		}
	}

	public HTTPResponse post(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), "OK", 200);
		String message = null;
		if(request.getParams() != null) {
			message = request.getParams().get("message");
			if(message != null) {
				response.setResponse(getResponseString(request, client.postMessage(message)));
			}
			else {
				response.setResponse(getResponseString(request, false));
			}
		}
		else {
			response.setResponse(getResponseString(request, false));
		}
		
		return response;
	}

	public HTTPResponse get(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader(request.getProtocol(), "OK", 200);
		response.setResponse(getResponseString(request, true));
		return response;
	}
	
	public String getResponseString(HTTPRequest request, boolean ok) {
		String responseString = "<html>"
				+ "\n\t<head><script>"
				+ "function getUrl() {"
				+ "document.getElementById(\'messageform\').action = \""+request.getPath()+"?message=\" + document.getElementById(\'message\').value;"
				+ "}</script>"
				+ "\n\t</head>"
				+ "\n\t<body>"
				+ "\n\t\t<h2>Slack Bot</h2>";
		if(!ok) {
			responseString += "\n\t\t\t<span style=\"color: Red;\">ERROR: Cannot post message! Something went wrong, try again!</span>";
		}
		
		responseString+= "\n\t\t<form method=\"POST\" id=\"messageform\" onsubmit=\"getUrl()\">"
				+ "\n\t\t\tWrite Message to Send as BOT:<br>"
				+ "\n\t\t\t<input type=\"text\" id=\"message\" name=\"message\">"
				+ "\n\t\t\t<br>"
				+ "\n\t\t</form>"
				+ "\n\t\t\t<button type=\"submit\" form=\"messageform\" value=\"Submit\">Submit</button>"
				+ "\n\t</body>"
				+ "\n</html>";
		return responseString;
	}

}
