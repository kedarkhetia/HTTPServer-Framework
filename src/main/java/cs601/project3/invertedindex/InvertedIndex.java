package cs601.project3.invertedindex;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import cs601.project3.invertedindex.AmazonDataStructure;
import cs601.project3.invertedindex.Tuple;

/**
 * InvertedIndex class implements Inverted Index data structure
 * which is used for efficient searching based on keywords. It supports
 * operations such as add to InvertedIndex, find asin, search text and
 * partially search text.
 * 
 * @author kmkhetia
 *
 */
public class InvertedIndex {
	// Static map for find function which stores the documents based on ASIN as index key.
	private static Map<String, List<AmazonDataStructure>> asinIndex = new HashMap<String, List<AmazonDataStructure>>();
	// Map to store inverted index for any document.
	private Map<String, List<Tuple>> invertedIndex = new HashMap<String, List<Tuple>>();
	
	/**
	 * The operation helps to add document to invertedIndex. 
	 * It sets the default value of frequency to 1.
	 * 
	 * @param text, element
	 * @return void
	 */
	public synchronized void add(String text, AmazonDataStructure element) {
		add(text, element, 1);
	}
	
	/**
	 * The operation helps to add document to invertedIndex
	 * 
	 * @param text, element, frequency
	 * @return void
	 */
	public synchronized void add(String text, AmazonDataStructure element, int frequency) {
		Tuple tuple = new Tuple();
		tuple.setObject(element);
		tuple.setFrequency(frequency);
		if(invertedIndex.containsKey(text)) {
			invertedIndex.get(text).add(tuple);
		}
		else {
			List<Tuple> list = new LinkedList<Tuple>();
			list.add(tuple);
			invertedIndex.put(text, list);
		}
	}
	
	/**
	 * This operation sorts documents based on 
	 * the frequency of the word appering in that
	 * document.
	 * 
	 * @return void
	 */
	public synchronized void sort(){
		for(String i : invertedIndex.keySet()) {
			Collections.sort(invertedIndex.get(i));
		}
	}
	
	/**
	 * The operation helps to add document to asinIndex if it 
	 * doesn't exist, otherwise it updates the index with 
	 * new document.
	 * 
	 * @param asin and element
	 * @return void
	 */
	public synchronized void addToAsinIndex(String asin, AmazonDataStructure element) {
		if(asinIndex.containsKey(asin)) {
			if(!asinIndex.get(asin).contains(element)) {
				asinIndex.get(asin).add(element);
			}
		}
		else {
			List<AmazonDataStructure> list = new LinkedList<AmazonDataStructure>();
			list.add(element);
			asinIndex.put(asin, list);
		}
	}
	
	/**
	 * The operation implements find operation on asinIndex 
	 * for searching any document for given ASIN.
	 * 
	 * @param asin
	 * @return void
	 */
	public static List<AmazonDataStructure> find(String asin) {
		return asinIndex.get(asin);
	}
	
	/**
	 * The operation implements search operation for a given text.
	 * It prints documents that contains given word. 
	 * 
	 * @param text
	 * @return void
	 */
	public List<Tuple> search(String text) {
		return invertedIndex.get(text.replaceAll("[^A-Za-z0-9 ]", "").toLowerCase());
	}
	
	/**
	 * The operation implements the partialsearch functionality.
	 * It prints documents that contains either the entire word 
	 * or even if the words are partially appearing in other words.
	 * For example 'he' word appears in 'help'
	 * 
	 * @param text
	 * @return void
	 */
	public List<Tuple> partialSearch(String text) {
		List<Tuple> result = new LinkedList<Tuple>();
		for(String i : invertedIndex.keySet()) {
			if(i.contains(text.replaceAll("[^A-Za-z0-9 ]", "").toLowerCase())) {
				if(invertedIndex.get(i) != null) {
					for(Tuple j : invertedIndex.get(i)) {
						result.add(j);
					}
				}
			}
		}
		return result;
	}
}
