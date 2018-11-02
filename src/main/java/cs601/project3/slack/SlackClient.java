package cs601.project3.slack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class SlackClient {
	HttpURLConnection connection;
	private static final String API_TOKEN = "xoxp-469171308900-470394212087-468620883488-7f6aaf6df2f123197d5bc244856ca25a";
	private static final String CHANNEL = "test";
	private static SlackClient slackClient;
	
	private SlackClient() {}
	
	public static SlackClient getInstance() {
		if(slackClient == null) {
			slackClient =  new SlackClient();
		}
		return slackClient;
	}
	
	public boolean postMessage(String message) throws IOException {
		connection = (HttpURLConnection) (new URL("https://slack.com/api/chat.postMessage?token="+API_TOKEN+"&channel="+CHANNEL+"&text=test:%20"+message)).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.connect();
		return validateResponse(connection);
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
		return slackResponse.getOk(); 
	}
}
