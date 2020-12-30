package com.suggestions.dto;

import java.util.ArrayList;
import java.util.List;

public class Suggestion {
	
	private List<SearchedCities> suggestions;

	public List<SearchedCities> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<SearchedCities> suggestions) {
		this.suggestions = suggestions;
	}

	public Suggestion() {
		super();
		this.suggestions = new ArrayList<SearchedCities>();
	}

}
