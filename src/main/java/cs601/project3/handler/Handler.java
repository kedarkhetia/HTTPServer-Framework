package cs601.project3.handler;

import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;
/**
 * This is handler interface. It acts as a communication
 * channel between server and client. Any request received
 * by server is passed to application using the handler
 * interface's handle method.
 * 
 * @author kmkhetia
 *
 */ 
public interface Handler {
	/**
	 * The method is called by server when new
	 * HTTPRequest comes to the server. This method
	 * is used by applications to handle different
	 * types of HTTPRequests and respond back using 
	 * HTTPResponse
	 * 
	 * @param request
	 * @return response
	 */
	public HTTPResponse handle(HTTPRequest request);
}
