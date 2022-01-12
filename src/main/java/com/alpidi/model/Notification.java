package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
	@JsonProperty("message") 
    public String getmessage() { 
		 return this.message; 
	} 
    public void setmessage(String message) { 
		 this.message = message; 
	} 
    String message;
    
    @JsonProperty("messageType") 
    public Number getmessageType() { 
		 return this.messageType; 
	} 
    public void setmessageType(Number messageType) { 
		 this.messageType = messageType; 
	} 
    Number messageType;
    
    @JsonProperty("priotity") 
    public Number getpriotity() { 
		 return this.priotity; 
	} 
    public void setpriotity(Number priotity) { 
		 this.priotity = priotity; 
	} 
    Number priotity;
    
    @JsonProperty("params") 
    public String getparams() { 
		 return this.params; 
	} 
    public void setparams(String params) { 
		 this.params = params; 
	} 
    String params;
}

