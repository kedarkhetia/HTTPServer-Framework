package cs601.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

import cs601.project3.config.Config;
import cs601.project3.httpserver.HTTPServer;
import cs601.project3.invertedindex.FindHandler;
import cs601.project3.invertedindex.InvertedIndex;
import cs601.project3.invertedindex.InvertedIndexBuilder;
import cs601.project3.invertedindex.QuestionAnswer;
import cs601.project3.invertedindex.Review;
import cs601.project3.invertedindex.ReviewSearchHandler;

public class InvertedIndexDriver {
	
	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();
		Config config = gson.fromJson(readFile(Paths.get(args[1])), Config.class);
		HTTPServer server = new HTTPServer(config.getInvertedIndexAppPort());
		new InvertedIndexBuilder()
				.setFilePath(Paths.get(config.getQaFilePath()))
				.setType(QuestionAnswer.class)
				.build();
		InvertedIndex invertedIndexReview = new InvertedIndexBuilder()
				.setFilePath(Paths.get(config.getReviewFilePath()))
				.setType(Review.class)
				.build();	
		server.addMapping("/find", new FindHandler(invertedIndexReview));
		server.addMapping("/reviewsearch", new ReviewSearchHandler(invertedIndexReview));
		server.start();
	}
	
	/**
	 * This function reads configuration file from the provided path.
	 * 
	 * @param path
	 * @throws IOException
	 * @return String
	 */
	public static String readFile(Path path) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = Files.newBufferedReader(path);
			String line;
			while((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			//log.error("Please check provided File IOException occured, ", e);
		}
		return sb.toString();
	}
}
