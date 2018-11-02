package cs601.project3.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cs601.project3.handler.Handler;

public class HTTPServer implements Runnable{
	private ServerSocket server;
	private ExecutorService threadPool;
	private ConcurrentHashMap<String, Handler> handlers;
	
	private static final int POOL_SIZE = 5;
	private static volatile boolean RUNNING_FLAG = true;
	
	public HTTPServer(int port) throws IOException {
		this.server = new ServerSocket(port);
		this.threadPool = Executors.newFixedThreadPool(POOL_SIZE);
		this.handlers = new ConcurrentHashMap<String, Handler>();
	}
	
	public void addMapping(String path, Handler handler) {
		handlers.put(path, handler);
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
		new Thread(this).start();
	}
	
	public void stop() {
		RUNNING_FLAG = false;
	}

}
