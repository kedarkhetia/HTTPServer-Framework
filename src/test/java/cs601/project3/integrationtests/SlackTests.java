package cs601.project3.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConnection;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;
import cs601.project3.slack.ChatHandler;
import cs601.project3.slack.SlackClient;

@RunWith(MockitoJUnitRunner.class)
public class SlackTests {
	HashMap<String, Handler> map;
	HTTPConnection connection;

	@Mock
	SlackClient client;
	
	@InjectMocks
	ChatHandler chatHandler = new ChatHandler(client);
	
	@Before
	public void preProcess() {
		map = new HashMap<String, Handler>();
		map.put("/slackbot", chatHandler);
		connection = new HTTPConnection(map, null, null, null);
	}
	
	@Test
	public void testSimpleGetMessage() {
		HTTPRequest request = new HTTPRequest("GET /slackbot HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testResponseGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /slackbot HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), chatHandler.getResponseString(true));
	}
	
	@Test
	public void testParamGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /slackbot?unused=notused HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamResponseGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /slackbot?unused=notused HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), chatHandler.getResponseString(true));
	}
	
	@Test
	public void testSimplePostHandler() {
		when(client.postMessage(null)).thenReturn(false);
		HTTPRequest request = new HTTPRequest("POST /slackbot HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testResponsePostHandler() {
		when(client.postMessage(null)).thenReturn(false);
		HTTPRequest request = new HTTPRequest("POST /slackbot HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), chatHandler.getResponseString(false));
	}
	
	@Test
	public void testParamPostHandler() {
		when(client.postMessage("somemessage")).thenReturn(true);
		HTTPRequest request = new HTTPRequest("POST /slackbot?message=somemessage HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamResponsePostHandler() {
		when(client.postMessage("somemessage")).thenReturn(true);
		HTTPRequest request = new HTTPRequest("POST /slackbot?message=somemessage HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), chatHandler.getResponseString(true));
	}
	
	@Test
	public void testMultiParamPostRequest() {
		when(client.postMessage("somemessage")).thenReturn(true);
		HTTPRequest request = new HTTPRequest("POST /slackbot?notused=unused&message=somemessage HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), chatHandler.getResponseString(true));
	}
}
