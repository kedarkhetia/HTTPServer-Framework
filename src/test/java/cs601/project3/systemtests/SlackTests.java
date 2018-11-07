package cs601.project3.systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cs601.project3.httpserver.HTTPConstants;
import cs601.project3.httpserver.HTTPServer;
import cs601.project3.invertedindex.FindHandler;
import cs601.project3.slack.ChatHandler;
import cs601.project3.slack.SlackClient;

@RunWith(MockitoJUnitRunner.class)
public class SlackTests {
	static HTTPServer server;
	static HttpURLConnection connection;
	
	@Mock
	static SlackClient client = new SlackClient("xoxp-469171308900-470394212087-468620883488-7f6aaf6df2f123197d5bc244856ca25a", "test");
	
	@InjectMocks
	static ChatHandler chatHandler = new ChatHandler(client);
	
	@BeforeClass
	public static void preProcessing() throws IOException {
		server = new HTTPServer(1080);
		server.addMapping("/slackbot", chatHandler);
		server.start();
	}
	
	private String validateResponse(URLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		String response = "";
		while((line = reader.readLine()) != null) {
			response += line;
		}
		return response;
	}
	
	@Test
	public void simpleChatTestStatus() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1080/slackbot").openConnection();
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_OK);
	}
	
	@Test
	public void checkFormChatTest() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1080/slackbot").openConnection();
		connection.connect();
		assertTrue(validateResponse(connection).contains("form"));
	}
	
	@Test
	public void checkInvalidMethodFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1080/slackbot").openConnection();
		connection.setRequestMethod("PUT");
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_MNA);
	}
	
	@Test
	public void checkInValidParamChat() throws MalformedURLException, IOException {
		when(client.postMessage(null)).thenReturn(false);
		connection = (HttpURLConnection) new URL("http://localhost:1080/slackbot").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = chatHandler.getResponseString(false).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkInvalidParamNameChat() throws MalformedURLException, IOException {
		when(client.postMessage(null)).thenReturn(false);
		connection = (HttpURLConnection) new URL("http://localhost:1080/slackbot").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = chatHandler.getResponseString(false).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkValidMultiBodyParamChat() throws MalformedURLException, IOException {
		when(client.postMessage("hey")).thenReturn(true);
		connection = (HttpURLConnection) new URL("http://localhost:1080/slackbot").openConnection();
		String params = "notused=unused&message=hey";
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length", Integer.toString(params.length()));
		connection.setUseCaches(false);
		try(PrintWriter wr = new PrintWriter(connection.getOutputStream())) {
		   wr.write(params);
		}
		connection.connect();
		assertEquals(validateResponse(connection).replaceAll("\\s+", ""), chatHandler.getResponseString(true).replaceAll("\\s+", ""));
		
	}
	
	@AfterClass
	public static void postProcessing() {
		server.stop();
	}
	
}
