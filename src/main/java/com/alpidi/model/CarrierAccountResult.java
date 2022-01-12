package com.alpidi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarrierAccountResult {
	@JsonProperty("next") 
    public String getNext() { 
		 return this.next; } 
    public void setNext(String next) { 
		 this.next = next; } 
    String next;
    @JsonProperty("previous") 
    public Object getPrevious() { 
		 return this.previous; } 
    public void setPrevious(Object previous) { 
		 this.previous = previous; } 
    Object previous;
    @JsonProperty("results") 
    public List<CarrierAccountDetails> getResults() { 
		 return this.results; } 
    public void setResults(List<CarrierAccountDetails> results) { 
		 this.results = results; } 
    List<CarrierAccountDetails> results;
}
