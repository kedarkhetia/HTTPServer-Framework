package cs601.project3.systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cs601.project3.httpserver.HTTPConstants;
import cs601.project3.httpserver.HTTPServer;
import cs601.project3.invertedindex.FindHandler;
import cs601.project3.invertedindex.InvertedIndex;
import cs601.project3.invertedindex.InvertedIndexBuilder;
import cs601.project3.invertedindex.QuestionAnswer;
import cs601.project3.invertedindex.Review;
import cs601.project3.invertedindex.ReviewSearchHandler;

public class InvertedIndexTests {
	static HTTPServer server;
	static HttpURLConnection connection;
	static InvertedIndex invertedIndex;
	
	@BeforeClass
	public static void preProcessing() throws IOException {
		server = new HTTPServer(1081);
		new InvertedIndexBuilder()
		.setFilePath(Paths.get("testQa.json"))
		.setType(QuestionAnswer.class)
		.build();
	
		invertedIndex = new InvertedIndexBuilder()
								.setFilePath(Paths.get("testReview.json"))
								.setType(Review.class)
								.build();	
		server.addMapping("/find", new FindHandler(invertedIndex));
		server.addMapping("/reviewsearch", new ReviewSearchHandler(invertedIndex));
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
	public void simpleFindTestStatus() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find").openConnection();
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_OK);
	}
	
	@Test
	public void checkFormFindTest() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find").openConnection();
		connection.connect();
		assertTrue(validateResponse(connection).contains("form"));
	}
	
	@Test
	public void checkInvalidPath() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/").openConnection();
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_PNF);
	}
	
	@Test
	public void checkInvalidMethodFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find").openConnection();
		connection.setRequestMethod("PUT");
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_MNA);
	}
	
	@Test
	public void checkValidMethodFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find?asin=B0024V0I0A").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_OK);
	}
	
	@Test
	public void checkInvalidParamFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find?asin=").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = new FindHandler(invertedIndex).getPostResponseString(null).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkInvalidParamNameFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find?sina=B0024V0I0A").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = new FindHandler(invertedIndex).getPostResponseString(null).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkValidMultiParamFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find?notused=unused&asin=B0024V0I0A").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = new FindHandler(invertedIndex).getPostResponseString(invertedIndex.find("B0024V0I0A")).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkValidMultiBodyParamFind() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/find").openConnection();
		String params = "notused=unused&asin=B0024V0I0A";
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length", Integer.toString(params.length()));
		connection.setUseCaches(false);
		try(PrintWriter wr = new PrintWriter(connection.getOutputStream())) {
		   wr.write(params);
		}
		connection.connect();
		assertEquals(validateResponse(connection).replaceAll("\\s+", ""), new FindHandler(invertedIndex).getPostResponseString(invertedIndex.find("B0024V0I0A")).replaceAll("\\s+", ""));
		
	}
	
	@Test
	public void simpleReviewTestStatus() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch").openConnection();
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_OK);
	}
	
	@Test
	public void checkFormReviewTest() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch").openConnection();
		connection.connect();
		assertTrue(validateResponse(connection).contains("form"));
	}
	
	@Test
	public void checkInvalidMethodReview() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch").openConnection();
		connection.setRequestMethod("PUT");
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_MNA);
	}
	
	@Test
	public void checkValidMethodReview() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch?query=hello").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		assertEquals(connection.getResponseCode(), HTTPConstants.STATUS_CODE_OK);
	}
	
	@Test
	public void checkInvalidParamReview() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch?query=").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = new ReviewSearchHandler(invertedIndex).getPostResponseString(null).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkInvalidParamNameReview() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch?uery=hello").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = new ReviewSearchHandler(invertedIndex).getPostResponseString(null).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkValidMultiParamReview() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch?notused=unused&query=hello").openConnection();
		connection.setRequestMethod("POST");
		connection.connect();
		String actual = validateResponse(connection).replaceAll("\\s+", "");
		String expected = new ReviewSearchHandler(invertedIndex).getPostResponseString(invertedIndex.search("hello")).replaceAll("\\s+", "");
		assertEquals(actual, expected);
	}
	
	@Test
	public void checkValidMultiBodyParamReview() throws MalformedURLException, IOException {
		connection = (HttpURLConnection) new URL("http://localhost:1081/reviewsearch").openConnection();
		String params = "notused=unused&query=hello";
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		connection.setRequestProperty("Content-Length", Integer.toString(params.length()));
		connection.setUseCaches(false);
		try(PrintWriter wr = new PrintWriter(connection.getOutputStream())) {
		   wr.write(params);
		}
		connection.connect();
		assertEquals(validateResponse(connection).replaceAll("\\s+", ""), new ReviewSearchHandler(invertedIndex).getPostResponseString(invertedIndex.search("hello")).replaceAll("\\s+", ""));
		
	}
	
	@AfterClass
	public static void postProcessing() {
		server.stop();
	}
	
}
