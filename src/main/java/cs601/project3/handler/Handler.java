package cs601.project3.handler;

import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public interface Handler {
	public HTTPResponse handle(HTTPRequest request);
}
