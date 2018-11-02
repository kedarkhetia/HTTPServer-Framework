package cs601.project3.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import cs601.project3.defaulthandler.DefaultHandler;
import cs601.project3.defaulthandler.ErrorHandler;
import cs601.project3.handler.Handler;

public class HTTPConnection implements Runnable {
	ConcurrentHashMap<String, Handler> handlers;
	Socket client;
	InputStream in;
	PrintWriter out;
	
	public HTTPConnection(ConcurrentHashMap<String, Handler> handlers, Socket client, InputStream in, PrintWriter out) {
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
			requestString += "\n" + readParams(in, length);
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

	private String readLine(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte b = (byte) in.read();
		while(b != '\n') {
			bout.write(b);
			b = (byte) in.read();
		}
		return new String(bout.toByteArray());
	}
	
	private String readParams(InputStream in, int length) throws IOException {
		byte[] bytes = new byte[length];
		int read = in.read(bytes);
		while(read < length) {
			read += in.read(bytes, read, (bytes.length-read));
		}
		return new String(bytes);
	}
}
