package cs601.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import cs601.project3.config.Config;
import cs601.project3.httpserver.HTTPServer;
import cs601.project3.slack.ChatHandler;
import cs601.project3.slack.SlackClient;

public class ChatDriver {
	private final static Logger log = LogManager.getLogger(ChatDriver.class);
	
	/**
	 * The function is main function that will configure
	 * chat application
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();
		log.info("Config file path received as " + args[1]);
		Config config = gson.fromJson(readFile(Paths.get(args[1])), Config.class);
		HTTPServer server = new HTTPServer(config.getChatAppPort());
		SlackClient slackClient = new SlackClient(config.getApiToken(), config.getApiChannel());
		server.addMapping("/slackbot", new ChatHandler(slackClient));
		server.start();
		log.info("ChatApplication started on port: " + config.getChatAppPort());
	}
	
	/**
	 * This function reads configuration file from the provided path.
	 * 
	 * @param path
	 * @throws IOException
	 * @return String
	 */
	public static String readFile(Path path) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = Files.newBufferedReader(path);
			String line;
			while((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			log.error("Please check provided File IOException occured, ", e);
		}
		return sb.toString();
	}
}
