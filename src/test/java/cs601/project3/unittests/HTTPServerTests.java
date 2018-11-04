package cs601.project3.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;

public class HTTPServerTests {
	
	// HTTP Request Tests
	
	/**
	 * Validates valid http request.
	 */
	@Test
	public void testSimpleRequest() {
		String requestString = "GET / HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertTrue(request.isValid());
	}
	
	/**
	 * Validates parameter request are parsed.
	 */
	@Test
	public void testParamRequest() {
		String requestString = "POST /abc?unused=notused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertTrue(request.isValid());
	}
	
	/**
	 * Validates http method is parsed properly.
	 */
	@Test
	public void testMethodRequestPost() {
		String requestString = "POST /abc?unused=notused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertEquals(request.getMethod(), "POST");
	}

	@Test
	public void testMethodRequestGet() {
		String requestString = "GET /abc?unused=notused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertEquals(request.getMethod(), "GET");
	}
	
	@Test
	public void testMethodRequest() {
		String requestString = "PUT /abc?unused=notused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertEquals(request.getMethod(), "PUT");
	}
	
	/**
	 * Validates request when method parsed is invalid.
	 */
	@Test
	public void testMethodRequestValidity() {
		String requestString = "PUT /abc?unused=notused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertFalse(request.isValid());
	}

	/**
	 * Validates request when request string is null.
	 */
	@Test
	public void testMethodRequestNull() {
		String requestString = null;
		HTTPRequest request = new HTTPRequest(requestString);
		assertFalse(request.isValid());
	}
	
	/**
	 * Checks if the request is able to read parameters.
	 */
	@Test
	public void testParametersParsing() {
		String requestString = "POST /abc?unused=notused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertEquals(request.getParams().get("unused"), "notused");
	}
	
	@Test
	public void testParametersParsingEmpty() {
		String requestString = "POST /abc?unused= HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		assertEquals(request.getParams().get("unused"), null);
	}
	
	@Test
	public void testMultipleParameters() {
		String requestString = "GET /abc?unused=notused&used=doused HTTP/1.1";
		HTTPRequest request = new HTTPRequest(requestString);
		Set<String> result = new HashSet<>();
		result.add("unused");
		result.add("used");
		assertEquals(request.getParams().keySet(), result);
	}
	
	// HTTP Response Tests
	
	/**
	 * Validates valid http response.
	 */
	@Test
	public void testSimpleResponse() {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader("HTTP/1.1", "OK", 200);
		response.setResponse("Response");
		assertTrue(response.isValid());
	}
	
	/**
	 * Validates invalid http response.
	 */
	@Test
	public void testPartialResponse() {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader("HTTP/1.1", null, 200);
		response.setResponse("Response");
		assertFalse(response.isValid());
	}
	
	@Test
	public void testNoResponse() {
		HTTPResponse response = new HTTPResponse();
		response.setResponseHeader("HTTP/1.1", "OK", 200);
		assertFalse(response.isValid());
	}
}
