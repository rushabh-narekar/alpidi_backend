package com.alpidi.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "sizes")
public class Sizes {
	@JsonProperty("id") 
    public String getid() { 
		 return this.id; } 
    public void setid(String id) { 
		 this.id = id; 
	} 
    String id;
    
    @JsonProperty("size") 
    public String getsize() { 
		 return this.size; 
	} 
    public void setsize(String size) { 
		 this.size = size; 
	} 
    String size;
}
