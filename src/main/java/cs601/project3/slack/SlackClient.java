package cs601.project3.slack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class SlackClient {
	private final static Logger log = LogManager.getLogger(SlackClient.class);

	private HttpURLConnection connection;
	private String apiToken;
	private String channel;
	
	public SlackClient(String apiToken, String channel) { 
		this.apiToken = apiToken;
		this.channel = channel;
	}
	
	public boolean postMessage(String message) {
		try {
			log.info("Sending message, " + message);
			connection = (HttpURLConnection) (new URL("https://slack.com/api/chat.postMessage?token="+apiToken+"&channel="+channel+"&text=test%20"+message)).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			connection.connect();
			return validateResponse(connection);
		} catch (IOException e) {
			log.error("IOException Occured, " + e);
		}
		return false;
	}
	
	private boolean validateResponse(URLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		String response = "";
		Gson json = new Gson();
		while((line = reader.readLine()) != null) {
			response += line;
		}
		SlackResponse slackResponse = json.fromJson(response, SlackResponse.class);
		log.info("Received response from slack, " + slackResponse);
		return slackResponse.getOk(); 
	}
}
