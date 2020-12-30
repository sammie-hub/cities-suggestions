package com.suggestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.suggestions.dto.LatLon;
import com.suggestions.dto.SearchedCities;
import com.suggestions.dto.Suggestion;
/*
 * -Class use DFS alogorithm to store and search city names.
 * 
 * - If name is the only parameter, then score will be calculated as number of chars matched/ total number of words.
 *  - If name ,latitude and long are the parameters, then score will be calculated as:-
 *  	dist = distance between (parameter coordinates - searched cities coordinates)
 *  	score = dist/highest distance calculated in searched cities coordinates
 */
public class AutoComplete {

	// The trie
	private RootNode trie;
	Suggestion sg = new Suggestion();

	private ReadTSV rtsv;

	CalculateDistance cd = new CalculateDistance();
	List<Double> listDist = new ArrayList<Double>();
	List<SearchedCities> listSearchedCities = new ArrayList<SearchedCities>();
	List<Suggestion> suggestions = new ArrayList<Suggestion>();
	HashMap<String, LatLon> coordinates = new HashMap<String, LatLon>();

	// Construct the trie from the dictionary
	public AutoComplete(List<String> dict, ReadTSV rtsv) {
		this.rtsv = rtsv;
		trie = new RootNode("");
		for (String s : dict)
			insertWord(s);
	}

	// Insert a word into the trie
	private void insertWord(String s) {
		// Iterate through each character in the string. If the character is not
		// already in the trie then add it
		RootNode curr = trie;
		for (int i = 0; i < s.length(); i++) {
			if (!curr.children.containsKey(s.charAt(i))) {
				curr.children.put(s.charAt(i), new RootNode(s.substring(0, i + 1)));
			}
			curr = curr.children.get(s.charAt(i));

			if (i == s.length() - 1) {
				curr.isWord = true;
				if (!coordinates.containsKey(curr.prefix)) {
					coordinates.put(curr.prefix,
							new LatLon(rtsv.map.get(s).getLongitude(), rtsv.map.get(s).getLatitude()));
				}
			}

		}
	}

	// Find all words in trie that start with prefix
	public Suggestion getWordsForPrefix(String pre, float latitude, float longitude) {
		List<String> results = new LinkedList<String>();

		// Iterate to the end of the prefix
		RootNode curr = trie;
		for (char c : pre.toCharArray()) {
			if (curr.children.containsKey(c)) {
				curr = curr.children.get(c);
			} else {
				listSearchedCities.clear();
				sg.setSuggestions(listSearchedCities);
				suggestions.add(sg);
				return sg;
			}
		}
		
		//calculates distance  and score from the user's query coodinates
		List<String> childwords = findAllChildWords(curr, results);
		if (latitude != 0.0f && longitude != 0.0f) {
			for (String s : childwords) {
				if (coordinates.containsKey(s)) {
					float lat1 = coordinates.get(s).getLat();
					float lat2 = latitude;
					float lon1 = coordinates.get(s).getLon();
					float lon2 = longitude;
					double calDist = cd.distance(lat1, lon1, lat2, lon2, "K");
					listDist.add(calDist);
				}
			}
			Collections.sort(listDist);
			int index = 0;
			for (String s : childwords) {

				calculateScoreFull(s, curr, index);
				index++;
			}
		} else {
			for (String s : childwords) {
				if (coordinates.containsKey(s)) {
					calculateScoreName(s, pre);
				}
			}
		}
		
		listSearchedCities.sort(new ScoreSorter()); // Sorting arraylist by score in descneding order
		sg.setSuggestions(listSearchedCities);
		suggestions.add(sg);
		return sg;
	}

	// Recursively find every child word
	private List<String> findAllChildWords(RootNode n, List<String> results) {
		if (n.isWord) {
			results.add(n.prefix);
		}
		for (Character c : n.children.keySet()) {
			findAllChildWords(n.children.get(c), results);
		}
		return results;
	}
	//Calculates score if parameters are name, latitude &longitude
	private List<SearchedCities> calculateScoreFull(String name, RootNode curr, int index) {
		SearchedCities sc = new SearchedCities();
		sc.setName(name);
		float lat = coordinates.get(name).getLat();
		float lon = coordinates.get(name).getLon();
		double maxdist = listDist.get(listDist.size() - 1);
		float score = (float) (listDist.get(index) / maxdist);
		sc.setLatitude(lat);
		sc.setLongitude(lon);
		sc.setScore("" + score);
		listSearchedCities.add(sc);
		return listSearchedCities;

	}
	//Calculates score if parameter is only name
	private List<Suggestion> calculateScoreName(String name, String pre) {
		SearchedCities sc = new SearchedCities();

		float lat1 = coordinates.get(name).getLat();
		float lon1 = coordinates.get(name).getLon();
		float score = (float) pre.length() / name.length();
		sc.setName(name);
		sc.setLatitude(lat1);
		sc.setLongitude(lon1);
		sc.setScore("" + score);
		listSearchedCities.add(sc);
		return suggestions;
	}
}

//Sorts result by score in descending order
class ScoreSorter implements Comparator<SearchedCities> {
	@Override
	public int compare(SearchedCities o1, SearchedCities o2) {
		return o2.getScore().compareToIgnoreCase(o1.getScore());
	}
}
