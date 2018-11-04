package cs601.project3.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cs601.project3.defaulthandler.MethodNotAllowed;
import cs601.project3.defaulthandler.PageNotFoundHandler;
import cs601.project3.httpserver.HTTPRequest;

public class HandlerTest {
	// Error Handler tests
	
	/**
	 * Validates valid responses from ErrorHandlers.
	 */
	@Test
	public void testSimpleErrorResponse() {
		MethodNotAllowed errorHandler = new MethodNotAllowed();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertTrue(errorHandler.handle(request).isValid());
	}
	
	@Test
	public void testResponseErrorStatus() {
		MethodNotAllowed errorHandler = new MethodNotAllowed();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertEquals(errorHandler.handle(request).getStatus(), "Method Not Allowed");
	}
	
	@Test
	public void testResponseErrorCode() {
		MethodNotAllowed errorHandler = new MethodNotAllowed();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertEquals(errorHandler.handle(request).getStatusCode(), 405);
	}
	
	@Test
	public void testResponseErrorHeader() {
		MethodNotAllowed errorHandler = new MethodNotAllowed();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertEquals(errorHandler.handle(request).getResponseHeader(), "HTTP/1.1 405 Method Not Allowed\n\r\n");
	}
	
	// Page Not Found Handler tests
	
	/**
	 * Validates valid responses from PageNotFoundHandler.
	 */
	@Test
	public void testSimplePNFResponse() {
		PageNotFoundHandler errorHandler = new PageNotFoundHandler();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertTrue(errorHandler.handle(request).isValid());
	}
	
	@Test
	public void testResponsePNFStatus() {
		PageNotFoundHandler errorHandler = new PageNotFoundHandler();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertEquals(errorHandler.handle(request).getStatus(), "error");
	}
	
	@Test
	public void testResponsePNFCode() {
		PageNotFoundHandler errorHandler = new PageNotFoundHandler();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertEquals(errorHandler.handle(request).getStatusCode(), 404);
	}
	
	@Test
	public void testResposePNFHeader() {
		PageNotFoundHandler errorHandler = new PageNotFoundHandler();
		HTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
		assertEquals(errorHandler.handle(request).getResponseHeader(), "HTTP/1.1 404 error\n\r\n");
	}
}
