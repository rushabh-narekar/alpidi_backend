package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryResponse {
	@JsonProperty("type") 
    public String getId() { 
		 return this.id; } 
    public void setId(String id) { 
		 this.id = id; } 
    String id;
}
