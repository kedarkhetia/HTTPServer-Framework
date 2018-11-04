package cs601.project3.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConnection;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class HTTPServerTests {
	HashMap<String, Handler> map;
	HTTPConnection connection;
	
	@Before
	public void preProcessing() {
		map = new HashMap<String, Handler>();
		connection = new HTTPConnection(map, null, null, null);
	}
	
	// Tests server response with different kinds of requests.
	
	/**
	 * Validates response on valid http request.
	 */
	@Test
	public void testSimpleGetResponse() {
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testHeaderGetResponse() {
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponseHeader(), "HTTP/1.1 200 OK\n\r\n");
	}
	
	@Test
	public void testResponseGetResponse() {
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), "GET");
	}
	
	@Test
	public void testSimplePostResponse() {
		HTTPRequest request = new HTTPRequest("POST / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testHeaderPostResponse() {
		HTTPRequest request = new HTTPRequest("POST / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponseHeader(), "HTTP/1.1 200 OK\n\r\n");
	}
	
	@Test
	public void testResponsePostResponse() {
		HTTPRequest request = new HTTPRequest("POST / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), "POST");
	}
	
	@Test
	public void testSimplePutResponse() {
		HTTPRequest request = new HTTPRequest("PUT / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testHeaderPutResponse() {
		HTTPRequest request = new HTTPRequest("PUT / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponseHeader(), "HTTP/1.1 405 Method Not Allowed\n\r\n");
	}
	
	@Test
	public void testResponsePutResponse() {
		HTTPRequest request = new HTTPRequest("PUT / HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertNotEquals(response.getResponse(), "PUT");
	}
	
	/**
	 * Validates response on invalid http request.
	 */
	@Test
	public void testInValidGetRequest() {
		HTTPRequest request = new HTTPRequest("/ GET HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getStatusCode(), 405);
	}
	
	@Test
	public void testInValidPathRequest() {
		HTTPRequest request = new HTTPRequest("GET /nopath HTTP/1.1");
		map.put("/", new MockHandler());
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getStatusCode(), 404);
	}
	
	static class MockHandler implements Handler {

		@Override
		public HTTPResponse handle(HTTPRequest request) {
			HTTPResponse response = new HTTPResponse();
			response.setResponseHeader(request.getProtocol(), "OK", 200);
			response.setResponse(request.getMethod());
			return response;
		}
		
	}
	
}
