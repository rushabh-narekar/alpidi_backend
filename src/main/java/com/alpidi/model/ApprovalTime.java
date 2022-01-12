package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "ApprovalTime")
public class ApprovalTime {
	@JsonProperty("id") 
    public String getId() { 
		 return this.id; } 
    public void setId(String id) { 
		 this.id = id; } 
    String id;
	@JsonProperty("duration") 
    public String getDuration() { 
		 return this.duration; } 
    public void setDuration(String duration) { 
		 this.duration = duration; } 
    String duration;
    
    @JsonProperty("durationtype") 
    public String getdurationtype() { 
		 return this.durationtype; } 
    public void setDurationtype(String durationtype) { 
		 this.durationtype = durationtype; } 
    String durationtype;
}
