package cs601.project3.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import cs601.project3.defaulthandler.MethodNotAllowed;
import cs601.project3.defaulthandler.PageNotFoundHandler;
import cs601.project3.handler.Handler;

public class HTTPConnection implements Runnable {
	private HashMap<String, Handler> handlers;
	private Socket client;
	private InputStream in;
	private PrintWriter out;
	
	public HTTPConnection(HashMap<String, Handler> handlers, Socket client, InputStream in, PrintWriter out) {
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
			int length = 0;
			while((line = readLine(in)) != null && !line.trim().isEmpty()) {
				requestString += line + "\n";
				if(line.startsWith("Content-Length:")) {
					length = Integer.parseInt(line.split(":")[1].trim());
				}
			}
			requestString += "\n" + readBody(in, length);
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
	
	public HTTPResponse getResponse(HTTPRequest request) {
		HTTPResponse response;
		if(!request.isValid()) {
			response = new MethodNotAllowed().handle(request);
		}
		else {
			if(handlers.containsKey(request.getPath())) {
				response = handlers.get(request.getPath()).handle(request);
			}
			else {
				response = new PageNotFoundHandler().handle(request);
			}
		}
		return response;
	}

	public String readLine(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte b = (byte) in.read();
		while(b != '\n' && b != -1) {
			bout.write(b);
			b = (byte) in.read();
		}
		return new String(bout.toByteArray());
	}
	
	public String readBody(InputStream in, int length) throws IOException {
		byte[] bytes = new byte[length];
		int read = in.read(bytes);
		while(read < length) {
			read += in.read(bytes, read, (bytes.length-read));
		}
		return new String(bytes);
	}
}
