package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "colors")
public class Colors {
	@JsonProperty("id") 
    public String getid() { 
		 return this.id; } 
    public void setid(String id) { 
		 this.id = id; 
	} 
    String id;
    
    @JsonProperty("color") 
    public String getcolor() { 
		 return this.color; 
	} 
    public void setcolor(String color) { 
		 this.color = color; 
	} 
    String color;
}
