package com.alpidi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipingProfiles {
	@JsonProperty("count") 
    public int getCount() { 
		 return this.count; } 
    public void setCount(int count) { 
		 this.count = count; } 
    int count;
    @JsonProperty("results") 
    public List<ShipingProfileDetails> getResults() { 
		 return this.results; } 
    public void setResults(List<ShipingProfileDetails> results) { 
		 this.results = results; } 
    List<ShipingProfileDetails> results;
}
