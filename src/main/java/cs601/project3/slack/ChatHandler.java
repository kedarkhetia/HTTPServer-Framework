package cs601.project3.slack;

import java.io.IOException;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class ChatHandler implements Handler{

	@Override
	public HTTPResponse handle(HTTPRequest request) {
		if(request.getMethod().equals("GET")) {
			return get(request);
		}
		else {
			try {
				return post(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Remove this null
		return null;
	}

	private HTTPResponse post(HTTPRequest request) throws IOException {
		HTTPResponse response = new HTTPResponse();
		response.setProtocol("HTTP/1.1");
		response.setStatus("OK");
		response.setStatusCode(200);
		String message = request.getParams().get("message");
		SlackClient.getInstance().postMessage(message);
		response.setResponse(getResponseString(request));
		return response;
	}

	private HTTPResponse get(HTTPRequest request) {
		HTTPResponse response = new HTTPResponse();
		response.setProtocol("HTTP/1.1");
		response.setStatus("OK");
		response.setStatusCode(200);
		response.setResponse(getResponseString(request));
		return response;
	}
	
	private String getResponseString(HTTPRequest request) {
		return "<html>"
				+ "\n\t<head><script>"
				+ "function getUrl() {"
				+ "document.getElementById(\'messageform\').action = \""+request.getPath()+"?message=\" + document.getElementById(\'message\').value;"
				+ "}</script>"
				+ "\n\t</head>"
				+ "\n\t<body>"
				+ "\n\t\t<h2>Slack Bot</h2>"
				+ "\n\t\t<form method=\"POST\" id=\"messageform\" onsubmit=\"getUrl()\">"
				+ "\n\t\t\tWrite Message to Send as BOT:<br>"
				+ "\n\t\t\t<input type=\"text\" id=\"message\" name=\"message\">"
				+ "\n\t\t\t<br>"
				+ "\n\t\t</form>"
				+ "\n\t\t\t<button type=\"submit\" form=\"messageform\" value=\"Submit\">Submit</button>"
				+ "\n\t</body>"
				+ "\n</html>";
	}

}
