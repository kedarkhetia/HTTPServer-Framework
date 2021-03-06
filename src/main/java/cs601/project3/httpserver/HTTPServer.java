package cs601.project3.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cs601.project3.handler.Handler;

/**
 * This class is responsible to establish HTTPConnection for each client.
 * 
 * @author kmkhetia
 *
 */
public class HTTPServer implements Runnable{
	private final static Logger log = LogManager.getLogger(HTTPServer.class);
	
	private ServerSocket server;
	private ExecutorService threadPool;
	private HashMap<String, Handler> handlers;
	private int port;
	
	private static volatile boolean RUNNING_FLAG = false;
	private static final int POOL_SIZE = 5;
	
	public HTTPServer(int port) {
		try {
			this.server = new ServerSocket(port);
			this.threadPool = Executors.newFixedThreadPool(POOL_SIZE);
			this.handlers = new HashMap<String, Handler>();
			this.port = port;
		} catch (IOException e) {
			log.error("IOException occured: " + e);
		}
	}
	
	/**
	 * This method will allow the application to add
	 * supported URIs.
	 * 
	 * @param path
	 * @param handler
	 */
	public void addMapping(String path, Handler handler) {
		if(path != null && handler != null) {
			handlers.put(path, handler);
		}
	}
	
	@Override
	public void run() {
		try {
			while(RUNNING_FLAG) {
				Socket client = server.accept();
				InputStream in = client.getInputStream();
				PrintWriter out = new PrintWriter(client.getOutputStream());
				threadPool.execute(new HTTPConnection(handlers, client, in, out));
			}
		} catch (IOException e) {
			log.error("IOException occured: " + e);
		}
	}
	
	/**
	 * This method will start the execution of Server
	 */
	public void start() {
		RUNNING_FLAG = true;
		new Thread(this).start();
	}
	/**
	 * This method will stop the execution of Server
	 */
	public void stop() {
		RUNNING_FLAG = false;
	}
	
	public int getPort() {
		return port;
	}
	
	public boolean isRunning() {
		return RUNNING_FLAG;
	}
}
