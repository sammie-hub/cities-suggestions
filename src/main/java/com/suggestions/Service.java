package com.suggestions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.suggestions.dto.Suggestion;

@RestController
public class Service {
	
	@Autowired
	private ReadTSV rt;
	
	@GetMapping("/suggestions")
	@ResponseBody
	public Suggestion suggestionsCity(@RequestParam(required = false) String q,
				@RequestParam(required = false) Float latitude, 
				@RequestParam(required = false) Float longitude) {
		rt.readFile();
		if(latitude==null || longitude==null)
		{
			latitude=0.0f;
			longitude=0.0f;
		}
		return rt.autocomplete.getWordsForPrefix(q, latitude, longitude);
		 
	}	
}