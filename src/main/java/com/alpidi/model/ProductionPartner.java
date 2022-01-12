package com.alpidi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductionPartner {
	@JsonProperty("count") 
    public int getCount() { 
		 return this.count; } 
    public void setCount(int count) { 
		 this.count = count; } 
    int count;
    @JsonProperty("results") 
    public List<ProductionPartnerDetails> getResult() { 
		 return this.results; } 
    public void setResult(List<ProductionPartnerDetails> results) { 
		 this.results = results; } 
    List<ProductionPartnerDetails> results;
}
