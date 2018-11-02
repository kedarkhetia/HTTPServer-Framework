package cs601.project3.invertedindex;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cs601.project3.invertedindex.InvertedIndex;
import cs601.project3.invertedindex.AmazonDataStructure;

/**
 * InvertedIndexBuilder class is used to build/create inverted index.
 * 
 * @author kmkhetia
 *
 */
public class InvertedIndexBuilder {
	private InvertedIndex invertedIndex = new InvertedIndex();
	private Path filePath;
	private Class<?> type;
	
	/**
	 * The operation helps to set the file path that contains
	 * the data for creating inverted index.
	 * 
	 * @param filePath
	 * @return InvertedIndexBuilder
	 */
	public InvertedIndexBuilder setFilePath(Path filePath) {
		this.filePath = filePath;
		return this;
	}
	
	/**
	 * The operation helps to set data type of inverted index.
	 * 
	 * @param type
	 * @return InvertedIndexBuilder
	 */
	public InvertedIndexBuilder setType(Class<?> type) {
		this.type = type;
		return this;
	}
	
	/**
	 * The operation helps to readfile and build invertedIndex
	 * 
	 * @return InvertedIndex
	 */
	public InvertedIndex build() throws IOException {
		BufferedReader in = Files.newBufferedReader(filePath, StandardCharsets.ISO_8859_1);
		String data;
		while((data = in.readLine()) != null) {
			try{
				Gson gson = new Gson();
				AmazonDataStructure element = (AmazonDataStructure) gson.fromJson(data, type);
				invertedIndex.addToAsinIndex(element.getAsin(), element);
				Map<String, Integer> frequencyMap = getFrequency(element.getText());
				for(String i : frequencyMap.keySet()) {
					invertedIndex.add(i, element, frequencyMap.get(i));
				}
			} catch(JsonSyntaxException e) {
				//Ignoring JsonSyntaxException
				//System.out.println(e.getMessage());
			}
		}
		invertedIndex.sort();
		return invertedIndex;
	}
	
	/**
	 * The operation helps to get the frequency of each word
	 * in given text.
	 * 
	 * @param text
	 * @return HashMap<String, Integer>
	 */
	private Map<String, Integer> getFrequency(String text) {
		String[] splitText = text.replaceAll("[^A-Za-z0-9 ]", "").toLowerCase().split(" ");
		Map<String, Integer> frequency = new HashMap<String, Integer>(); 
		for(String i : splitText) {
			if(frequency.containsKey(i)) {
				frequency.put(i, frequency.get(i) + 1);
			}
			else {
				frequency.put(i, 1);
			}
		}
		return frequency;
	}
}

