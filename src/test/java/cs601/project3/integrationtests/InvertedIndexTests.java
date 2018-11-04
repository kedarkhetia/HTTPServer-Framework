package cs601.project3.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cs601.project3.handler.Handler;
import cs601.project3.httpserver.HTTPConnection;
import cs601.project3.httpserver.HTTPRequest;
import cs601.project3.httpserver.HTTPResponse;
import cs601.project3.invertedindex.FindHandler;
import cs601.project3.invertedindex.InvertedIndex;
import cs601.project3.invertedindex.InvertedIndexBuilder;
import cs601.project3.invertedindex.QuestionAnswer;
import cs601.project3.invertedindex.Review;
import cs601.project3.invertedindex.ReviewSearchHandler;

public class InvertedIndexTests {
	InvertedIndex invertedIndex;
	FindHandler findHandler;
	ReviewSearchHandler reviewSearchHandler;
	HashMap<String, Handler> map;
	HTTPConnection connection;
	
	@Before
	public void preProcessing() throws IOException {
		new InvertedIndexBuilder()
			.setFilePath(Paths.get("testQa.json"))
			.setType(QuestionAnswer.class).build();
		invertedIndex = new InvertedIndexBuilder()
			.setFilePath(Paths.get("testReview.json"))
			.setType(Review.class).build();
		findHandler = new FindHandler(invertedIndex);
		reviewSearchHandler = new ReviewSearchHandler(invertedIndex);
		map = new HashMap<String, Handler>();
		map.put("/find", findHandler);
		map.put("/reviewsearch", reviewSearchHandler);
		connection = new HTTPConnection(map, null, null, null);
	}
	
	// Find Handler Tests.
	
	/**
	 * Validating find api with http requests
	 */
	@Test
	public void testSimpleFindGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /find HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testResponseFindGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /find HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), findHandler.getGetResponseString());
	}
	
	@Test
	public void testParamFindGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /find?unused=notused HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamResponseFindGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /find?unused=notused HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), findHandler.getGetResponseString());
	}
	
	@Test
	public void testSimpleFindPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /find HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testResponseFindPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /find HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), findHandler.getPostResponseString(null));
	}
	
	@Test
	public void testParamFindPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /find?asin=B0023B1364 HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamResponseFindPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /find?asin=B0023B1364 HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(),findHandler.getPostResponseString(invertedIndex.find("B0023B1364")));
	}
	
	@Test
	public void testMultiParamFindPostRequest() {
		HTTPRequest request = new HTTPRequest("POST /find?notused=unused&asin=B0023B1364 HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), findHandler.getPostResponseString(invertedIndex.find("B0023B1364")));
	}
	
	@Test
	public void testSimpleSearchGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /reviewsearch HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testResponseSearchGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /reviewsearch HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), reviewSearchHandler.getGetResponseString());
	}
	
	@Test
	public void testParamSearchGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /reviewsearch?unused=notused HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamResponseSearchGetHandler() {
		HTTPRequest request = new HTTPRequest("GET /reviewsearch?unused=notused HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), reviewSearchHandler.getGetResponseString());
	}
	
	@Test
	public void testSimpleSearchPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /reviewsearch HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamSearchPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /reviewsearch?query=B0023B1364 HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertTrue(response.isValid());
	}
	
	@Test
	public void testParamResponseSearchPostHandler() {
		HTTPRequest request = new HTTPRequest("POST /reviewsearch?query=B0023B1364 HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(),reviewSearchHandler.getPostResponseString(invertedIndex.search("B0023B1364")));
	}
	
	@Test
	public void testMultiParamSearcPostRequest() {
		HTTPRequest request = new HTTPRequest("POST /reviewsearch?notused=unused&query=B0023B1364 HTTP/1.1");
		HTTPResponse response = connection.getResponse(request);
		assertEquals(response.getResponse(), reviewSearchHandler.getPostResponseString(invertedIndex.search("B0023B1364")));
	}
}
