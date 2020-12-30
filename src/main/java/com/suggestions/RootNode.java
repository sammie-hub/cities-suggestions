package com.suggestions;

import java.util.HashMap;

public class RootNode {
	
	String prefix;
    HashMap<Character, RootNode> children;
    
    // Does this node represent the last character in a word?
    boolean isWord;
    
     RootNode(String prefix) {
        this.prefix = prefix;
        this.children = new HashMap<Character, RootNode>();
    }
}
