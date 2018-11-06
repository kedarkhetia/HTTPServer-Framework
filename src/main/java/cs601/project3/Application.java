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
import cs601.project3.slack.SlackClient;

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
		SlackClient slackClient = new SlackClient("xoxp-469171308900-470394212087-468620883488-7f6aaf6df2f123197d5bc244856ca25a", "test");
		server.addMapping("/slackbot", new ChatHandler(slackClient));
		server.start();
	}
}
