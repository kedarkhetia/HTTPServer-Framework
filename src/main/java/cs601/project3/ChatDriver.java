package cs601.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

import cs601.project3.config.Config;
import cs601.project3.httpserver.HTTPServer;
import cs601.project3.slack.ChatHandler;
import cs601.project3.slack.SlackClient;

public class ChatDriver {
	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();
		Config config = gson.fromJson(readFile(Paths.get(args[1])), Config.class);
		HTTPServer server = new HTTPServer(config.getChatAppPort());
		SlackClient slackClient = new SlackClient(config.getApiToken(), config.getApiChannel());
		server.addMapping("/slackbot", new ChatHandler(slackClient));
		server.start();
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
			//log.error("Please check provided File IOException occured, ", e);
		}
		return sb.toString();
	}
}
