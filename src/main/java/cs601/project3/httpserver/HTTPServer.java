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

import cs601.project3.handler.Handler;

public class HTTPServer implements Runnable{
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
			//TODO log something.
		}
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		RUNNING_FLAG = true;
		new Thread(this).start();
	}
	
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
