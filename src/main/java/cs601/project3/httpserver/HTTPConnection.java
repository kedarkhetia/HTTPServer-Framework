package cs601.project3.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cs601.project3.defaulthandler.MethodNotAllowed;
import cs601.project3.defaulthandler.PageNotFoundHandler;
import cs601.project3.handler.Handler;

/**
 * This class is responsible to handle the request
 * and response once the connection is established.
 * 
 * @author kmkhetia
 *
 */ 
public class HTTPConnection implements Runnable {
	private final static Logger log = LogManager.getLogger(HTTPConnection.class);
	
	private HashMap<String, Handler> handlers;
	private Socket client;
	private InputStream in;
	private PrintWriter out;
	
	public HTTPConnection(HashMap<String, Handler> handlers, Socket client, InputStream in, PrintWriter out) {
		this.handlers = handlers;
		this.client = client;
		this.in = in;
		this.out = out;
		log.info("Received client: " + client);
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
			log.info("Received request: " + requestString);
			String requestParams = readBody(in, length);
			log.info("Received request body: " + requestParams);
			HTTPRequest request = new HTTPRequest(requestString);
			request.setParams(requestParams);
			HTTPResponse response = getResponse(request);
			out.write(response.getResponseHeader());
			log.info("Responding response header: " + response.getResponseHeader());
			out.write(response.getResponse());
			log.info("Responding response body: " + response.getResponse());
			out.close();
			in.close();
			client.close();
		} catch (IOException e) {
			log.error("IOException Occured, " + e);
		}
	}
	
	/**
	 * It takes HTTPRequest as input to verify,
	 * that the request is valid and it calls 
	 * respectiver handler based on the request.
	 * 
	 * @param request
	 * @return response
	 */
	public HTTPResponse getResponse(HTTPRequest request) {
		HTTPResponse response;
		if(!request.isValid()) {
			response = new MethodNotAllowed().handle(request);
			log.info("Invalid request in Method, valid:" + request.isValid() );
		}
		else {
			if(handlers.containsKey(request.getPath())) {
				response = handlers.get(request.getPath()).handle(request);
				log.info("Invalid path requested, Path:" + request.getPath() + " valid:" + request.isValid());
			}
			else {
				response = new PageNotFoundHandler().handle(request);
				log.info("Invalid path requested, Path:" + request.getPath() + " valid:" + request.isValid());
			}
		}
		return response;
	}
	/**
	 * This method reads the input request
	 * from input stream.
	 * 
	 * @param in
	 * @return string
	 * @throws IOException
	 */
	public String readLine(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte b = (byte) in.read();
		while(b != '\n' && b != -1) {
			bout.write(b);
			b = (byte) in.read();
		}
		return new String(bout.toByteArray());
	}
	/**
	 * It reads the body of a requests, where
	 * http parameters might be sent.
	 * 
	 * @param in
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public String readBody(InputStream in, int length) throws IOException {
		byte[] bytes = new byte[length];
		int read = in.read(bytes);
		while(read < length) {
			read += in.read(bytes, read, (bytes.length-read));
		}
		return new String(bytes);
	}
}
