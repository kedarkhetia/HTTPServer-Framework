package cs601.project3.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import cs601.project3.defaulthandler.DefaultHandler;
import cs601.project3.defaulthandler.ErrorHandler;
import cs601.project3.handler.Handler;

public class HTTPConnection implements Runnable {
	ConcurrentHashMap<String, Handler> handlers;
	Socket client;
	BufferedReader in;
	PrintWriter out;
	
	public HTTPConnection(ConcurrentHashMap<String, Handler> handlers, Socket client, BufferedReader in, PrintWriter out) {
		this.handlers = handlers;
		this.client = client;
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		try {
			String requestString = "";
			String line;
			while(!(line = in.readLine()).isEmpty()) {
				requestString += line + "\n";
			}
			HTTPRequest request = new HTTPRequest(requestString);
			HTTPResponse response = getResponse(request);
			out.write(response.getResponseHeader());
			out.write(response.getResponse());
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private HTTPResponse getResponse(HTTPRequest request) {
		HTTPResponse response;
		if(!request.isValid()) {
			response = new ErrorHandler().handle(request);
		}
		else {
			if(handlers.containsKey(request.getPath())) {
				response = handlers.get(request.getPath()).handle(request);
			}
			else {
				response = new DefaultHandler().handle(request);
			}
		}
		return response;
	}

}
