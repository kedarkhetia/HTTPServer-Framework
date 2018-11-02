package cs601.project3;

import java.io.IOException;
import java.nio.file.Paths;

import cs601.project3.httpserver.HTTPServer;
import cs601.project3.invertedindex.FindHandler;
import cs601.project3.invertedindex.InvertedIndex;
import cs601.project3.invertedindex.InvertedIndexBuilder;
import cs601.project3.invertedindex.QuestionAnswer;
import cs601.project3.invertedindex.Review;
import cs601.project3.invertedindex.ReviewSearchHandler;
import cs601.project3.slack.ChatHandler;

public class Application {
	public static void main(String[] args) throws IOException {
		HTTPServer server = new HTTPServer(1080);
		new InvertedIndexBuilder()
			.setFilePath(Paths.get("qa.json"))
			.setType(QuestionAnswer.class)
			.build();
		
		InvertedIndex invertedIndexReview = new InvertedIndexBuilder()
									.setFilePath(Paths.get("review.json"))
									.setType(Review.class)
									.build();				
		server.addMapping("/find", new FindHandler(invertedIndexReview));
		server.addMapping("/reviewsearch", new ReviewSearchHandler(invertedIndexReview));
		server.addMapping("/slackbot", new ChatHandler());
		server.start();
	}
}
